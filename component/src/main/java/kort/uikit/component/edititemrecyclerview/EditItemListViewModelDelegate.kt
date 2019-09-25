package kort.uikit.component.edititemrecyclerview

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kort.tool.toolbox.livedata.Event
import kort.tool.toolbox.livedata.aware
import timber.log.Timber
import kotlin.reflect.KClass

/**
 * Created by Kort on 2019/9/13.
 */
data class FocusEvent(
    val focusAt: Int,
    val needCallReBind: Boolean = false
)

interface EditItemListViewModelDelegate<T : ItemModel> {
    val list: LiveData<MutableList<T>>
    val focusItemAt: LiveData<Event<FocusEvent>>
    val addItemAt: LiveData<Event<Int>>
    val changeItemAt: LiveData<Event<IntRange>>
    val deleteItemAt: LiveData<Event<Int>>
    fun onDelete(position: Int)
    fun onWrapLine(
        position: Int,
        beforeWrapLineText: String,
        afterWrapLineText: String
    )

    fun onTextChange(position: Int, changedText: String, aware: Boolean = false)
    fun requestFocus(position: Int, needCallReBind: Boolean = false)
}

abstract class DefaultNumberListViewModelDelegate<T : ItemModel> :
    EditItemListViewModelDelegate<T> {

    protected val _list: MutableLiveData<MutableList<T>> = MutableLiveData()
    override val list: LiveData<MutableList<T>> = _list

    protected val itemCount get() = _list.value?.size ?: 0

    protected var _id = 0
    protected val generateId get() = (_id++).toString()

    private val _focusItemAt: MutableLiveData<Event<FocusEvent>> = MutableLiveData()
    override val focusItemAt: LiveData<Event<FocusEvent>> = _focusItemAt

    private val _addItemAt: MutableLiveData<Event<Int>> = MutableLiveData()
    override val addItemAt: LiveData<Event<Int>> = _addItemAt


    private val _changeItemAt: MutableLiveData<Event<IntRange>> = MutableLiveData()
    override val changeItemAt: LiveData<Event<IntRange>> = _changeItemAt

    private val _deleteItemAt: MutableLiveData<Event<Int>> = MutableLiveData()
    override val deleteItemAt: LiveData<Event<Int>> = _deleteItemAt

    @CallSuper
    override fun requestFocus(position: Int, needCallReBind: Boolean) {
        _focusItemAt.value = Event(
            FocusEvent(
                position,
                needCallReBind
            )
        )
    }

    @CallSuper
    override fun onDelete(position: Int) {
        Timber.d("call at $position")

        if (itemCount > 1) {
            Timber.d("at $position")
            _list.value?.removeAt(position)
            _list.aware()
            _deleteItemAt.value = Event(position)

            if (itemCount != position) {
                awareChangeItemToListLast(position)
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
        _addItemAt.value = Event(newItemPosition)
        awareChangeItemToListLast(newItemPosition + 1)
        requestFocus(newItemPosition)
    }

    abstract fun generateNewItem(
        position: Int,
        title: String,
        id: String
    ): T

    @CallSuper
    override fun onTextChange(position: Int, changedText: String, aware: Boolean) {
        Timber.d("onTextChange at $position: $changedText")
        _list.value?.let {
            it[position].title = changedText
            if (aware) {
                _list.aware()
                _changeItemAt.value = Event(position..position)
            }
        }
    }

    private fun awareChangeItemToListLast(startPosition: Int) {
        _changeItemAt.value = Event(startPosition..(_list.value?.lastIndex ?: startPosition))
    }
}