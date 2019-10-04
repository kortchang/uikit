package kort.uikit.component.itemEditText

import androidx.databinding.BindingAdapter
import timber.log.Timber

/**
 * Created by Kort on 2019/9/13.
 */
object BaseItemEditTextBindingAdapter {
    @BindingAdapter("onWrapLine")
    @JvmStatic
    fun BaseItemEditText.onWrapLine(listener: EditItemListener.OnWrapLineListener) {
        onWrapLineListener = listener
    }

    @BindingAdapter("onDelete")
    @JvmStatic
    fun BaseItemEditText.onDelete(listener: EditItemListener.OnDeleteListener) {
        onDeleteListener = listener
    }

    @BindingAdapter("onTextChange")
    @JvmStatic
    fun BaseItemEditText.onTextChange(listener: EditItemListener.OnTextChange) {
        onTextChangeListener = listener
    }
}
