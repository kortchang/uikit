package kort.uikit.component.edititemlist.nested

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kort.tool.toolbox.livedata.aware
import kort.uikit.component.edititemlist.ListEventObserver
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegateInterface
import kort.uikit.component.edititemlist.ItemModel
import timber.log.Timber
import java.lang.Exception
import kotlin.reflect.KClass

/**
 * Created by Kort on 2019/9/25.
 */
interface NestedListViewModelDelegateInterface<P : ItemModel, C : ItemModel> :
    SingleListViewModelDelegateInterface<ItemModel> {
    val parentList: LiveData<List<P>>
    val childMap: LiveData<Map<String, List<C>>>
}

abstract class NestedListViewModelDelegate<P : ItemModel, C : ChildItemModel>(
    private val ParentClass: KClass<P>,
    private val childClass: KClass<C>
) : ListEventObserver(), NestedListViewModelDelegateInterface<P, C> {
    override val listLastIndex: Int? get() = _list.value?.size

    private var _id = 0
    protected val generateChildId get() = (_id++).toString()

    protected var _parentList: MutableLiveData<MutableList<P>> = MutableLiveData(mutableListOf())
    override val parentList: LiveData<List<P>> = _parentList.map { it.toList() }

    protected var _childMap: MutableLiveData<MutableMap<String, MutableList<C>>> =
        MutableLiveData(mutableMapOf())
    override val childMap: LiveData<Map<String, List<C>>> =
        _childMap.map { mutableMap -> mutableMap.mapValues { it.value.toList() } }

    protected val _list: MediatorLiveData<MutableList<ItemModel>> =
        MediatorLiveData<MutableList<ItemModel>>().apply {
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

    private fun combineList(
        parentList: MutableList<P>,
        childMap: MutableMap<String, MutableList<C>>
    ): MutableList<ItemModel> {
        val originChildMap = childMap
        val currentList = mutableListOf<ItemModel>()
        parentList.forEach {
            currentList.add(it)
            var childList = childMap[it.id]
            if (childList.isNullOrEmpty()) {
                childList = generateDefaultChildList(it.id)
                childMap[it.id] = childList
                _childMap.value?.put(it.id, childList)
            }
            currentList.addAll(childList)
        }

        val currentChild = currentList.subtract(parentList)
        addRestOfChildToCurrentList(currentList, originChildMap, currentChild)

        return currentList
    }

    private fun addRestOfChildToCurrentList(
        currentList: MutableList<ItemModel>,
        originChildMap: MutableMap<String, MutableList<C>>,
        currentChildList: Set<ItemModel>
    ) {
        val originChildList = originChildMap.values.flatten()
        val restOfChild = originChildList.subtract(currentChildList)
        if (restOfChild.isNotEmpty()) currentList.addAll(restOfChild)
    }

    override val list: LiveData<MutableList<ItemModel>> = _list

    protected abstract fun generateParentItem(id: String, title: String, order: Int = 0): P
    protected abstract fun generateChildItem(
        id: String,
        parentId: String,
        title: String = "",
        order: Int = 0,
        childOrder: Int = 0
    ): C

    protected fun generateDefaultChildList(parentId: String): MutableList<C> =
        mutableListOf(generateChildItem(generateChildId, parentId))

    fun setParentList(list: MutableList<P>) {
        _parentList.value = list
        _parentList.aware()
    }

    override fun onDelete(position: Int) {
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
                        requestFocus(focusAt)
                    }
                } else
                    Timber.d("Rest of one item in childList")
            }
        }
    }

    override fun onWrapLine(position: Int, beforeWrapLineText: String, afterWrapLineText: String) {
        val newItemPosition = position + 1
        onTextChange(position, beforeWrapLineText, true)
        getChildItem(position) { item ->
            getChildPosition(item) { childPosition ->
                val newItem = generateChildItem(
                    generateChildId,
                    item.parentId,
                    afterWrapLineText,
                    newItemPosition,
                    childPosition + 1
                )
                addItemToChildList(item.parentId, newItem)
            }
        }

        sendAddEventAt(newItemPosition)
        sendChangeEventToLastIndex(newItemPosition + 1)
        requestFocus(newItemPosition)
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

    override fun requestFocus(position: Int) {
        sendFocusEventAt(position)
    }
}