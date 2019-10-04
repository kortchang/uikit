package kort.uikit.component.edititemlist

import androidx.lifecycle.LiveData
import kort.tool.toolbox.livedata.Event

/**
 * Created by Kort on 2019/9/29.
 */
interface EditItemViewModelDelegate {
    fun onDelete(position: Int)
    fun onWrapLine(position: Int, beforeWrapLineText: String, afterWrapLineText: String)
    fun onTextChange(position: Int, changedText: String, aware: Boolean = false)
}

interface ParentNestedEditItemViewModelDelegate : EditItemViewModelDelegate {
    val parentListEventSender: ListEventSenderInterface
    val parentFocusItemAt: LiveData<Event<Int>> get() = parentListEventSender._focusItemAt
    val parentAddItemAt: LiveData<Event<Int>> get() = parentListEventSender._addItemAt
    val parentDeleteItemAt: LiveData<Event<Int>> get() = parentListEventSender._deleteItemAt
    val parentChangeItemAt: LiveData<Event<IntRange>> get() = parentListEventSender._changeItemAt
    fun parentOnDelete(position: Int)
    fun parentOnWrapLine(position: Int, beforeWrapLineText: String, afterWrapLineText: String)
    fun parentOnTextChange(position: Int, changedText: String, aware: Boolean = false)
}

interface ChildNestedEditItemViewModelDelegate {
    fun childOnDelete(position: Int)
    fun childOnWrapLine(position: Int, beforeWrapLineText: String, afterWrapLineText: String)
    fun childOnTextChange(position: Int, changedText: String, aware: Boolean = false)
}