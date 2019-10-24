package kort.uikit.component.edittextlist

import kort.uikit.component.itemEditText.CheckBoxEditText

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
    fun onCheckedChange(view:CheckBoxEditText, position: Int, checked: Boolean)
}