package kort.uikit.component.edititemrecyclerview

/**
 * Created by Kort on 2019-09-12.
 */
interface EditListItemListener {
    interface OnDeleteListener {
        fun onDelete()
    }

    interface OnWrapLineListener {
        fun onWrapLine(beforeWrapLineText: String, afterWrapLineText: String)
    }

    interface OnTextChange {
        fun onTextChange(changedText: String)
    }
}

abstract class EditListItemListenerClass : EditListItemListener.OnTextChange,
    EditListItemListener.OnDeleteListener, EditListItemListener.OnWrapLineListener {
    override fun onTextChange(changedText: String) {}
    override fun onDelete() {}
    override fun onWrapLine(beforeWrapLineText: String, afterWrapLineText: String) {}
}