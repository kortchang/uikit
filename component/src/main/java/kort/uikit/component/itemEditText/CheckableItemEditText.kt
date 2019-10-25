package kort.uikit.component.itemEditText

import android.content.Context
import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import androidx.core.text.set
import androidx.core.text.toSpannable
import kort.tool.toolbox.view.obtainStyleAndRecycle
import kort.uikit.component.R
import timber.log.Timber

/**
 * Created by Kort on 2019/10/25.
 */
interface Checkable {
    var isChecked: Boolean
    fun toggle(): Boolean
}

abstract class CheckableItemEditText(context: Context, attrs: AttributeSet) :
    BaseItemEditText(context, attrs), Checkable {
    override var isChecked: Boolean = false
        set(value) {
            twoText.forEach {
                it.paintFlags = if (value) Paint.STRIKE_THRU_TEXT_FLAG else 0
                it.setTextAppearance(if (value) inactiveTextAppearanceRes else textAppearanceRes)
            }
            field = value
        }

    override fun toggle(): Boolean {
        isChecked = !isChecked
        return isChecked
    }

    override fun obtainStyle(attrs: AttributeSet) {
        super.obtainStyle(attrs)
        context.obtainStyleAndRecycle(attrs, R.styleable.CheckableItemEditText) {
            isChecked = getBoolean(R.styleable.CheckBoxEditText_android_checkable, false)
        }
    }
}