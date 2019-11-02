package kort.uikit.component.edittextlist.single

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kort.uikit.component.edittextlist.*
import timber.log.Timber
import kotlin.reflect.KClass

/**
 * Created by Kort on 2019/9/13.
 */
interface SingleListDelegateInterface<T : EditItemModel> : SingleEditItemListListener<T>,
    EditItemDelegate, ListEventObserverInterface {
    val list: LiveData<MutableList<T>>
    val listEventSenderObserver: ListEventSenderObserverInterface
}

open class SingleListDelegate<T : EditItemModel>(
    override var itemClass: KClass<T>,
    override val listEventSenderObserver: ListEventSenderObserverInterface = ListEventSenderObserver()
) : SingleListDelegateInterface<T>,
    ListEventSenderObserverInterface by listEventSenderObserver {
    var _id: Int = 0

    val generateId get() = (_id++).toString()
    protected open var _list: MutableLiveData<MutableList<T>> = MutableLiveData()

    override val list: LiveData<MutableList<T>> get() = _list
    @CallSuper
    override fun onDelete(position: Int) = listOnDelete(_list, this, position)

    @CallSuper
    override fun onWrapLine(position: Int, beforeWrapLineText: String, afterWrapLineText: String) =
        listOnWrapLine(_list, this, position, beforeWrapLineText, afterWrapLineText, generateId)

    @CallSuper
    override fun onTextChange(position: Int, changedText: String, aware: Boolean) =
        listOnTextChange(_list, this, position, changedText, aware)

    @CallSuper
    override fun addNewItemAtLast() = listAddNewItemAtLast(_list, this, generateId)
}
