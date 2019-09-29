package kort.uikit.component.edititemlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kort.tool.toolbox.livedata.Event

/**
 * Created by Kort on 2019/9/27.
 */
interface ListEventObserverInterface {
    val focusItemAt: LiveData<Event<Int>>
    val addItemAt: LiveData<Event<Int>>
    val changeItemAt: LiveData<Event<IntRange>>
    val deleteItemAt: LiveData<Event<Int>>
}

abstract class ListEventObserver : ListEventObserverInterface {
    protected abstract val listLastIndex: Int?

    protected val _focusItemAt: MutableLiveData<Event<Int>> = MutableLiveData()
    override val focusItemAt: LiveData<Event<Int>> = _focusItemAt

    protected val _addItemAt: MutableLiveData<Event<Int>> = MutableLiveData()
    override val addItemAt: LiveData<Event<Int>> = _addItemAt


    protected val _changeItemAt: MutableLiveData<Event<IntRange>> = MutableLiveData()
    override val changeItemAt: LiveData<Event<IntRange>> = _changeItemAt

    protected val _deleteItemAt: MutableLiveData<Event<Int>> = MutableLiveData()
    override val deleteItemAt: LiveData<Event<Int>> = _deleteItemAt

    protected fun sendFocusEventAt(position: Int) {
        _focusItemAt.value = Event(position)
    }

    protected fun sendAddEventAt(position: Int) {
        _addItemAt.value = Event(position)
    }

    protected fun sendDeleteEventAt(position: Int) {
        _deleteItemAt.value = Event(position)
    }

    protected fun sendChangeEventAt(position: Int) {
        _changeItemAt.value = Event(position..position)
    }

    protected fun sendChangeEventToLastIndex(startPosition: Int) {
        _changeItemAt.value = Event(startPosition..(listLastIndex ?: startPosition))
    }
}