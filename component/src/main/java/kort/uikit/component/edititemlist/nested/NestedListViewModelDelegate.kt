package kort.uikit.component.edititemlist.nested

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kort.tool.toolbox.livedata.aware
import kort.uikit.component.edititemlist.*
import timber.log.Timber
import java.lang.Exception
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * Created by Kort on 2019/9/25.
 */
interface NestedListViewModelDelegateInterface<P : EditItemModel, C : ChildEditItemModel, TWO : EditItemModel> :
    EditItemViewModelDelegate, ListEventSenderObserverInterface {
    val list: LiveData<MutableList<TWO>>
    val parentList: LiveData<MutableList<P>>
    val childMap: LiveData<MutableMap<String, MutableList<C>>>
    val parentClass: KClass<P>
    val childClass: KClass<C>
    fun setParentList(list: MutableList<P>)
}

open class NestedListViewModelDelegate<P : EditItemModel, C : ChildEditItemModel, TWO : EditItemModel>(
    override val parentClass: KClass<P>,
    override val childClass: KClass<C>
) : NestedListViewModelDelegateInterface<P, C, TWO>,
    ListEventSenderObserverInterface by ListEventSenderObserver() {
    val listLastIndex: Int? get() = _list.value?.size

    protected open var _parentId = 0
    protected open val generateParentId get() = (_parentId++).toString()

    protected open var _childId = 0
    protected open val generateChildId get() = (_childId++).toString()

    var _parentList: MutableLiveData<MutableList<P>> =
        MutableLiveData(mutableListOf())
    override val parentList: LiveData<MutableList<P>> get() = _parentList

    protected open var _childMap: MutableLiveData<MutableMap<String, MutableList<C>>> =
        MutableLiveData(mutableMapOf())
    override val childMap: LiveData<MutableMap<String, MutableList<C>>> get() = _childMap

    protected open val _list: MediatorLiveData<MutableList<TWO>> =
        MediatorLiveData<MutableList<TWO>>().apply {
            var parent = _parentList.value!!
            var child = _childMap.value!!
            addSource(_parentList) {
                parent = it
                value = combineList(it, child)
            }

            addSource(_childMap) {
                child = it
                value = combineList(parent, it)
            }
        }
    override val list: LiveData<MutableList<TWO>> get() = _list

    private fun combineList(
        parentList: MutableList<P>,
        childMap: MutableMap<String, MutableList<C>>
    ): MutableList<TWO> {
        val currentList = mutableListOf<TWO>()
        parentList.forEach {
            currentList.add(it as TWO)
            var childList = childMap[it.id]
            if (childList.isNullOrEmpty()) {
                childList = generateDefaultChildList(it.id)
                childMap[it.id] = childList
                _childMap.value?.put(it.id, childList)
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
        _parentList.value = list
        _parentList.aware()
    }

    fun onDelete(_childMap: MutableLiveData<MutableMap<String, MutableList<C>>>, position: Int) {
        Timber.d("onDelete at $position")
        getChildItem(position) { item ->
            Timber.d("onDelete item is childItem")
            _childMap.value?.let { childMap ->
                val childList = childMap[item.parentId] ?: mutableListOf()
                if (childList.size > 1) {
                    getChildPosition(item) { childPosition ->
                        childList.remove(item)
                        _childMap.aware()
                        val focusAt = if (childPosition == 0) position else position - 1
                        sendFocusEventAt(focusAt)
                    }
                } else
                    Timber.d("Rest of one item in childList")
            }
        }
    }

    override fun onDelete(position: Int) = onDelete(_childMap, position)

    override fun onWrapLine(position: Int, beforeWrapLineText: String, afterWrapLineText: String) {
        val newItemPosition = position + 1
        onTextChange(position, beforeWrapLineText, true)
        getChildItem(position) { item ->
            getChildPosition(item) { childPosition ->
                val newItem = generateChildItem(
                    generateChildId,
                    item.parentId,
                    afterWrapLineText,
                    newItemPosition
                )
                addItemToChildList(item.parentId, newItem)
            }
        }

        sendAddEventAt(newItemPosition)
        sendChangeEventToLastIndex(newItemPosition + 1, listLastIndex)
        sendFocusEventAt(newItemPosition)
    }

    protected fun getChildItem(position: Int, block: (C) -> Unit) {
        _list.value?.let {
            val item = it[position]
            if (childClass.isInstance(item)) {
                block(item as C)
            }
        }
    }

    protected fun getChildPosition(item: C, block: (Int) -> Unit) {
        _childMap.value?.let { childMap ->
            Timber.d("getChildPosition childMap: $childMap")
            val childPosition = childMap[item.parentId]?.indexOfFirst { it.id == item.id }
                ?: throw Exception("Cannot find the item in childList")
            block(childPosition)
        }
    }


    private fun addItemToChildList(
        parentId: String,
        item: C
    ) {
        _childMap.value?.let {
            it[parentId]?.add(item)
            Timber.d("_childMap: $_childMap")
        }
        _childMap.aware()
    }

    override fun onTextChange(
        position: Int,
        changedText: String,
        aware: Boolean
    ) {
        getChildItem(position) { item ->
            getChildPosition(item) { childPosition ->
                _childMap.value?.let {
                    it[item.parentId]?.get(childPosition)?.title = changedText
                    if (aware) {
                        _childMap.aware()
                        sendChangeEventAt(position)
                    }
                }
            }
        }
    }
}