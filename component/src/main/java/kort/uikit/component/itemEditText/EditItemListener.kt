package kort.uikit.component.itemEditText

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

