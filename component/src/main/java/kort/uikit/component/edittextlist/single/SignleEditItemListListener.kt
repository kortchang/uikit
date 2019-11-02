package kort.uikit.component.edittextlist.single

import androidx.lifecycle.MutableLiveData
import kort.tool.toolbox.livedata.aware
import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.edittextlist.ListEventSenderInterface
import timber.log.Timber
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * Created by Kort on 2019/10/3.
 */
interface SingleEditItemListListener<T : EditItemModel> {
    val itemClass: KClass<T>
    fun generateNewItem(position: Int, title: String, id: String): T =
        itemClass.createInstance().apply {
            this.id = id
            this.title = title
            this.order = position
        }

    fun listOnDelete(
        _list: MutableLiveData<MutableList<T>>,
        listEventSender: ListEventSenderInterface,
        position: Int
    ) {
        _list.value?.let { list ->
            if (list.size > 0) {
                Timber.d("delete at $position")
                list.removeAt(position)
                reOrderList(list, position)
                _list.aware()

                listEventSender.run {
                    sendDeleteEventAt(position)
                    if (list.isNotEmpty()) {
                        sendChangeEventToLastIndex(position, list.lastIndex)
                        val focusAt = if (position == 0) position else position - 1
                        sendFocusEventAt(focusAt)
                    }
                }
            }
        }
    }

    fun reOrderList(list: MutableList<T>, startPosition: Int) {
        for (index in startPosition..list.lastIndex) {
            list[index].order = index
        }
    }

    fun listOnWrapLine(
        _list: MutableLiveData<MutableList<T>>,
        listEventSender: ListEventSenderInterface,
        position: Int,
        beforeWrapLineText: String,
        afterWrapLineText: String,
        newItemId: String
    ) {
        _list.value?.let { list ->
            Timber.d("wrapLine at $position")
//            listOnTextChange(_list, listEventSender, position, beforeWrapLineText, true)
            val newItemPosition = position + 1
            val newItem = generateNewItem(newItemPosition, afterWrapLineText, newItemId)
            list.add(newItemPosition, newItem)
            reOrderList(list, newItemPosition + 1)
            _list.postValue(list)
//            _list.aware()
            listEventSender.run {
                sendAddEventAt(newItemPosition)
                sendChangeEventToLastIndex(newItemPosition + 1, list.lastIndex)
                sendFocusEventAt(newItemPosition)
            }
        }
    }

    fun listAddNewItemAtLast(
        _list: MutableLiveData<MutableList<T>>,
        listEventSender: ListEventSenderInterface,
        newItemId: String
    ) {
        _list.value?.let {
            val newItemPosition = it.size
            it.add(generateNewItem(newItemPosition, "", newItemId))
            _list.aware()

            listEventSender.run {
                sendAddEventAt(newItemPosition)
                sendFocusEventAt(newItemPosition)
                sendChangeEventAt(newItemPosition + 1)
            }
        }
    }

    fun listOnTextChange(
        _list: MutableLiveData<MutableList<T>>,
        listEventSender: ListEventSenderInterface,
        position: Int,
        changedText: String,
        aware: Boolean = false
    ) {
        Timber.d("onTextChange at $position: $changedText")
        _list.value?.let {
            it[position].title = changedText
            if (aware) {
                _list.aware()
                listEventSender.sendChangeEventAt(position)
            }
        }
    }
}