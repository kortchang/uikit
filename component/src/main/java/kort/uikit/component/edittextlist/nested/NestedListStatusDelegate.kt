package kort.uikit.component.edittextlist.nested

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kort.tool.toolbox.DataStatus
import kort.tool.toolbox.livedata.aware
import kort.uikit.component.edittextlist.*
import kort.uikit.component.itemEditText.ChildEditItemModel
import timber.log.Timber
import java.lang.Exception
import java.text.FieldPosition
import kotlin.math.max
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * Created by Kort on 2019/9/25.
 */
interface NestedListStatusDelegateInterface<P : EditItemModel, C : ChildEditItemModel, TWO : EditItemModel> :
    EditItemDelegate, ListEventSenderObserverInterface {
    val list: LiveData<DataStatus<MutableList<TWO>>>
    val parentList: LiveData<DataStatus<MutableList<P>>>
    val childMap: LiveData<DataStatus<MutableMap<String, MutableList<C>>>>
    val parentClass: KClass<P>
    val childClass: KClass<C>
    fun setParentLiveData(liveData: MutableLiveData<DataStatus<MutableList<P>>>)
    fun setChildLiveData(liveData: MutableLiveData<DataStatus<MutableMap<String, MutableList<C>>>>)
    fun setParentList(list: MutableList<P>)
    fun setChildMap(map: MutableMap<String, MutableList<C>>)
}

open class NestedListStatusDelegate<P : EditItemModel, C : ChildEditItemModel, TWO : EditItemModel>(
    override val parentClass: KClass<P>,
    override val childClass: KClass<C>
) : NestedListStatusDelegateInterface<P, C, TWO>,
    ListEventSenderObserverInterface by ListEventSenderObserver() {
    val listLastIndex: Int?
        get() {
            val status = _list.value
            return if (status is DataStatus.Success) status.result.size else null
        }

    protected open var _parentId = 0
    protected open val generateParentId get() = (_parentId++).toString()

    protected open var _childId = 0
    protected open val generateChildId get() = (_childId++).toString()

    override fun setParentLiveData(liveData: MutableLiveData<DataStatus<MutableList<P>>>) {
        _parentList = liveData
        _parentList.aware()
    }

    protected open var _parentList: MutableLiveData<DataStatus<MutableList<P>>> =
        MutableLiveData(DataStatus.Loading())
    override val parentList: LiveData<DataStatus<MutableList<P>>> get() = _parentList

    override fun setChildLiveData(liveData: MutableLiveData<DataStatus<MutableMap<String, MutableList<C>>>>) {
        _childMap = liveData
        _childMap.aware()
    }

    protected open var _childMap: MutableLiveData<DataStatus<MutableMap<String, MutableList<C>>>> =
        MutableLiveData(DataStatus.Loading())
    override val childMap: LiveData<DataStatus<MutableMap<String, MutableList<C>>>> get() = _childMap

    protected open val _list: MediatorLiveData<DataStatus<MutableList<TWO>>> by lazy {
        MediatorLiveData<DataStatus<MutableList<TWO>>>().apply {
            addSource(_parentList) {
                _childMap.value?.let { child ->
                    value = it.combine(child) { p, c -> combineList(p, c) }
                }
            }

            addSource(_childMap) {
                _parentList.value?.let { parent ->
                    value = it.combine(parent) { c, p -> combineList(p, c) }
                }
            }
        }
    }

    override val list: LiveData<DataStatus<MutableList<TWO>>> get() = _list

    private fun combineList(
        parentList: MutableList<P>,
        childMap: MutableMap<String, MutableList<C>>
    ): MutableList<TWO> {
        val currentList = mutableListOf<TWO>()
        parentList.forEach { parent ->
            currentList.add(parent as TWO)
            var childList = childMap[parent.id]
            if (childList.isNullOrEmpty()) {
                childList = generateDefaultChildList(parent.id)
                childMap[parent.id] = childList
                _childMap.value?.isSuccess {
                    it[parent.id] = childList
                }
            }
            currentList.addAll(childList as Collection<TWO>)
        }
        return currentList
    }

    protected fun generateParentItem(id: String, title: String, order: Int = 0): P =
        parentClass.createInstance().apply {
            this.id = id
            this.title = title
            this.order = order
        }

    protected fun generateChildItem(
        id: String,
        parentId: String,
        title: String = "",
        order: Int = 0
    ): C = childClass.createInstance().apply {
        this.id = id
        this.parentId = parentId
        this.title = title
        this.order = order
    }

    protected fun generateDefaultChildList(parentId: String): MutableList<C> =
        mutableListOf(generateChildItem(generateChildId, parentId))

    override fun setParentList(list: MutableList<P>) {
        _parentList.postValue(DataStatus.Success(list))
    }

    override fun setChildMap(map: MutableMap<String, MutableList<C>>) {
        _childMap.postValue(DataStatus.Success(map))
    }

    open fun onDelete(
        _parentList: MutableLiveData<DataStatus<MutableList<P>>>,
        _childMap: MutableLiveData<DataStatus<MutableMap<String, MutableList<C>>>>,
        position: Int
    ) {
        _list.value?.isSuccess {
            val item = it[position]
            when {
                parentClass.isInstance(item) -> parentOnDelete(item as P, position, item.order)
                childClass.isInstance(item) -> getChildPosition(item as C) { childPosition ->
                    childOnDelete(item, position, childPosition)
                }
            }
        }
    }

    private fun parentOnDelete(item: P, position: Int, parentPosition: Int) {
        _parentList.value?.isSuccess { parentList ->
            parentList.removeAt(parentPosition)
            _childMap.value?.isSuccess {
                val deleteChildList = it.remove(item.id)
                if (deleteChildList != null) {
                    sendDeleteEventAt(position, position + deleteChildList.size)
                    _childMap.aware()
                } else {
                    sendDeleteEventAt(position)
                }
                _parentList.aware()
                if ((position - 1) > 0) sendFocusEventAt(position - 1)
            }
        }
    }

    private fun childOnDelete(item: C, position: Int, childPosition: Int) {
        _childMap.value?.isSuccess { childMap ->
            val childList = childMap[item.parentId] ?: mutableListOf()
            if (childList.size > 1) {
                childList.remove(item)
                _childMap.aware()
                val focusAt = if (childPosition == 0) position else position - 1
                sendFocusEventAt(focusAt)
            }
        }
    }

    override fun onDelete(position: Int) = onDelete(_parentList, _childMap, position)

    override fun onWrapLine(position: Int, beforeWrapLineText: String, afterWrapLineText: String) {
        val listValue = _list.value
        if (listValue is DataStatus.Success) {
            val onWrapLineItem = listValue.result[position]
            val newItemPosition = position + 1
            if (childClass.isInstance(onWrapLineItem)) {
                Timber.d("child onWrapLine position: $position")
                onWrapLineOnChildItem(
                    position,
                    newItemPosition,
                    beforeWrapLineText,
                    afterWrapLineText
                )
            } else if (parentClass.isInstance(onWrapLineItem)) {
                Timber.d("parent onWrapLine position: $position")
                onTextChange(position, onWrapLineItem.title, true)
            }
            sendFocusEventAt(newItemPosition)
        }
    }

    open fun onWrapLineOnChildItem(
        position: Int,
        newItemPosition: Int,
        beforeWrapLineText: String,
        afterWrapLineText: String
    ) {
        onTextChange(position, beforeWrapLineText, true)
        getChildItem(position) { item ->
            getChildPosition(item) { childPosition ->
                val newItem = generateChildItem(
                    generateChildId,
                    item.parentId,
                    afterWrapLineText,
                    childPosition
                )
                addItemToChildList(item.parentId, newItem)
            }
        }
        sendAddEventAt(newItemPosition)
        sendChangeEventToLastIndex(newItemPosition + 1, listLastIndex)
    }

    protected open fun getChildItem(position: Int, block: (C) -> Unit) {
        _list.value?.isSuccess {
            val item = it[position]
            if (childClass.isInstance(item)) {
                block(item as C)
            }
        }
    }

    protected open fun getChildPosition(item: C, block: (Int) -> Unit) {
        _childMap.value?.isSuccess { childMap ->
            val childPosition = (childMap[item.parentId]
                ?: throw Exception("Cannot fin the parentId:${item.parentId} in childMap"))
                .indexOfFirst { it.id == item.id }
            block(childPosition)
        }
    }

    protected open fun addItemToChildList(
        parentId: String,
        item: C
    ) {
        _childMap.value?.isSuccess {
            it[parentId]?.add(item)
            _childMap.aware()
        }
    }

    override fun onTextChange(
        position: Int,
        changedText: String,
        aware: Boolean
    ) {
        _list.value?.isSuccess {
            val onTextChangeItem = it[position]
            if (childClass.isInstance(onTextChangeItem))
                getChildItem(position) { item ->
                    getChildPosition(item) { childPosition ->
                        _childMap.value?.isSuccess { childMap ->
                            childMap[item.parentId]?.get(childPosition)?.title = changedText
                            if (aware) {
                                _childMap.aware()
                                sendChangeEventAt(position)
                            }
                        }
                    }
                }
            else if (parentClass.isInstance(onTextChangeItem)) {
                onTextChangeItem as P
                _parentList.value?.isSuccess {
                    it[onTextChangeItem.order].title = changedText
                }
                if (aware) {
                    _parentList.aware()
                    sendChangeEventAt(position)
                }
            }
        }
    }

    override fun addNewItemAtLast() {
        _parentList.value?.isSuccess {
            _list.value?.isSuccess { listValue ->
                Timber.d("addNewItemAtLast")
                val newPositionInList = listValue.size
                val parent = generateParentItem(generateParentId, "", it.size)
                it.add(parent)
                _parentList.aware()

                sendAddEventAt(newPositionInList)
                sendFocusEventAt(newPositionInList)
            }
        }
    }
}