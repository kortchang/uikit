package kort.uikit.component.itemEditText

import androidx.databinding.BindingAdapter

/**
 * Created by Kort on 2019/9/13.
 */
object BaseItemEditTextBindingAdapter {
    @BindingAdapter("onWrapLine")
    @JvmStatic
    fun BaseItemEditText.onWrapLine(listener: ItemEditTextListener.OnWrapLineListener?) {
        onWrapLineListener = listener
    }

    @BindingAdapter("onDelete")
    @JvmStatic
    fun BaseItemEditText.onDelete(listener: ItemEditTextListener.OnDeleteListener?) {
        onDeleteListener = listener
    }

    @BindingAdapter("onTextChange")
    @JvmStatic
    fun BaseItemEditText.onTextChange(listener: ItemEditTextListener.OnTextChange?) {
        onTextChangeListener = listener
    }
}

object CheckBoxItemEditTextBindingAdapter {
    @BindingAdapter("onCheckedChange")
    @JvmStatic
    fun CheckBoxEditText.onCheckedChange(listener: OnCheckedChangeListener?) {
        onCheckedChangeListener = listener
    }
}