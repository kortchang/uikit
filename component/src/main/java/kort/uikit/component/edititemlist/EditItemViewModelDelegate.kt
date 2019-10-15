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
    fun addNewItemAtLast()
}