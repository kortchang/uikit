package kort.uikit.component.edititemlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kort.tool.toolbox.livedata.Event
import kotlin.math.min

/**
 * Created by Kort on 2019/9/27.
 */
interface ListEventObserverInterface {
    val focusItemAt: LiveData<Event<Int>>
    val addItemAt: LiveData<Event<Int>>
    val deleteItemAt: LiveData<Event<Int>>
    val changeItemAt: LiveData<Event<IntRange>>
}

interface ListEventSenderInterface {
    val _focusItemAt: MutableLiveData<Event<Int>>
    val _addItemAt: MutableLiveData<Event<Int>>
    val _changeItemAt: MutableLiveData<Event<IntRange>>
    val _deleteItemAt: MutableLiveData<Event<Int>>

    fun sendFocusEventAt(_focusItemAt: MutableLiveData<Event<Int>>, position: Int) {
        _focusItemAt.value = Event(position)
    }

    fun sendAddEventAt(_addItemAt: MutableLiveData<Event<Int>>, position: Int) {
        _addItemAt.value = Event(position)
    }

    fun sendDeleteEventAt(_deleteItemAt: MutableLiveData<Event<Int>>, position: Int) {
        _deleteItemAt.value = Event(position)
    }

    fun sendChangeEventAt(_changeItemAt: MutableLiveData<Event<IntRange>>, position: Int) {
        _changeItemAt.value = Event(position..position)
    }

    fun sendChangeEventToLastIndex(
        _changeItemAt: MutableLiveData<Event<IntRange>>,
        startPosition: Int,
        listLastIndex: Int?
    ) {
        val end = listLastIndex ?: 0
        val start = min(startPosition, end)
        _changeItemAt.value = Event(start..end)
    }

    fun sendFocusEventAt(position: Int) = sendFocusEventAt(_focusItemAt, position)
    fun sendAddEventAt(position: Int) = sendAddEventAt(_addItemAt, position)
    fun sendDeleteEventAt(position: Int) = sendDeleteEventAt(_deleteItemAt, position)
    fun sendChangeEventAt(position: Int) = sendChangeEventAt(_changeItemAt, position)
    fun sendChangeEventToLastIndex(startPosition: Int, listLastIndex: Int?) =
        sendChangeEventToLastIndex(_changeItemAt, startPosition, listLastIndex)
}

open class ListEventSender : ListEventSenderInterface {
    override val _focusItemAt: MutableLiveData<Event<Int>> = MutableLiveData()
    override val _addItemAt: MutableLiveData<Event<Int>> = MutableLiveData()
    override val _changeItemAt: MutableLiveData<Event<IntRange>> = MutableLiveData()
    override val _deleteItemAt: MutableLiveData<Event<Int>> = MutableLiveData()
}

interface ListEventSenderObserverInterface : ListEventSenderInterface, ListEventObserverInterface

open class ListEventSenderObserver : ListEventSenderObserverInterface,
    ListEventSenderInterface by ListEventSender() {
    override val focusItemAt: LiveData<Event<Int>> get() = _focusItemAt
    override val addItemAt: LiveData<Event<Int>> get() = _addItemAt
    override val changeItemAt: LiveData<Event<IntRange>> get() = _changeItemAt
    override val deleteItemAt: LiveData<Event<Int>> get() = _deleteItemAt
}