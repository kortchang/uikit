package kort.uikit.component.edittextlist

/**
 * Created by Kort on 2019/9/29.
 */
interface EditItemDelegate {
    fun onDelete(position: Int)
    fun onWrapLine(position: Int, beforeWrapLineText: String, afterWrapLineText: String)
    fun onTextChange(position: Int, changedText: String, aware: Boolean = false)
    fun addNewItemAtLast()
}

interface CheckableEditItemDelegate : EditItemDelegate {
    fun onCheckedChange(position: Int, checked: Boolean)
}