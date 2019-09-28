package kort.uikit.component.edititemrecyclerview

import androidx.lifecycle.LiveData

/**
 * Created by Kort on 2019-09-12.
 */
interface EditItemListener {
    interface OnDeleteListener {
        fun onDelete() {}
    }

    interface OnWrapLineListener {
        fun onWrapLine(beforeWrapLineText: String, afterWrapLineText: String) {}
    }

    interface OnTextChange {
        fun onTextChange(changedText: String) {}
    }
}

interface EditItemListListener {
    fun onDelete(position: Int)
    fun onWrapLine(
        position: Int,
        beforeWrapLineText: String,
        afterWrapLineText: String
    )

    fun onTextChange(position: Int, changedText: String, aware: Boolean = false)
    fun requestFocus(position: Int)
}