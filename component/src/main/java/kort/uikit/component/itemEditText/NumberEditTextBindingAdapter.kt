package kort.uikit.component.itemEditText

import androidx.databinding.BindingAdapter
import kort.uikit.component.edititemrecyclerview.EditListItemListener

/**
 * Created by Kort on 2019/9/13.
 */
object NumberEditTextBindingAdapter {
    @BindingAdapter("onWrapLine")
    @JvmStatic
    fun BaseItemEditText.onWrapLine(listener: EditListItemListener.OnWrapLineListener) {
        onWrapLineListener = listener
    }

    @BindingAdapter("onDelete")
    @JvmStatic
    fun BaseItemEditText.onDelete(listener: EditListItemListener.OnDeleteListener) {
        onDeleteListener = listener
    }

    @BindingAdapter("onTextChange")
    @JvmStatic
    fun BaseItemEditText.onTextChange(listener: EditListItemListener.OnTextChange) {
        onTextChangeListener = listener
    }
}
