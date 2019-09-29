package kort.uikit.component.edititemlist.single

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kort.tool.toolbox.livedata.aware
import kort.uikit.component.edititemlist.EditItemListListener
import kort.uikit.component.edititemlist.ItemModel
import kort.uikit.component.edititemlist.ListEventObserver
import kort.uikit.component.edititemlist.ListEventObserverInterface
import timber.log.Timber

/**
 * Created by Kort on 2019/9/13.
 */
interface EditItemListViewModelDelegateInterface<T : ItemModel> : ListEventObserverInterface,
    EditItemListListener {
    val list: LiveData<MutableList<T>>
}

abstract class EditItemListViewModelDelegate<T : ItemModel> : ListEventObserver(),
    EditItemListViewModelDelegateInterface<T> {
    override val listLastIndex: Int? get() = _list.value?.lastIndex
    protected val _list: MutableLiveData<MutableList<T>> = MutableLiveData()
    override val list: LiveData<MutableList<T>> = _list

    protected val itemCount get() = _list.value?.size ?: 0

    protected var _id = 0
    protected val generateId get() = (_id++).toString()

    @CallSuper
    override fun requestFocus(position: Int) {
        sendFocusEventAt(position)
    }

    @CallSuper
    override fun onDelete(position: Int) {
        Timber.d("call at $position")

        if (itemCount > 1) {
            Timber.d("at $position")
            _list.value?.removeAt(position)
            _list.aware()
            sendDeleteEventAt(position)

            if (itemCount != position) {
                sendChangeEventToLastIndex(position)
            }

            val focusAt = if (position == 0) {
                position
            } else {
                position - 1
            }
            requestFocus(focusAt)
        }
    }


    @CallSuper
    override fun onWrapLine(
        position: Int,
        beforeWrapLineText: String,
        afterWrapLineText: String
    ) {
        Timber.d("wrapLine at $position")
        onTextChange(position, beforeWrapLineText, true)
        val newItemPosition = position + 1
        val newItem = generateNewItem(newItemPosition, afterWrapLineText, generateId)
        Timber.d("createNewItemAdd() newItem: $newItem position: $newItemPosition")
        _list.value?.add(newItemPosition, newItem)
        _list.aware()

        sendAddEventAt(newItemPosition)
        sendChangeEventToLastIndex(newItemPosition + 1)
        requestFocus(newItemPosition)
    }

    abstract fun generateNewItem(position: Int, title: String, id: String): T

    @CallSuper
    override fun onTextChange(position: Int, changedText: String, aware: Boolean) {
        Timber.d("onTextChange at $position: $changedText")
        _list.value?.let {
            it[position].title = changedText
            if (aware) {
                _list.aware()
                sendChangeEventAt(position)
            }
        }
    }
}