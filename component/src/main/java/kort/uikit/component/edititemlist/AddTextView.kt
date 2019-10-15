package kort.uikit.component.edititemlist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.*
import androidx.constraintlayout.widget.ConstraintLayout
import kort.tool.toolbox.view.obtainStyleAndRecycle
import kort.tool.toolbox.view.privateGet
import kort.tool.toolbox.view.private_get_message
import kort.uikit.component.R
import kort.uikit.component.databinding.AddTextviewBinding
import timber.log.Timber

/**
 * Created by Kort on 2019/10/13.
 */
class AddTextView(context: Context, private val attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    private val inflater = LayoutInflater.from(context)
    val binding: AddTextviewBinding = AddTextviewBinding.inflate(inflater, this, true)

    private val flow get() = binding.flowAddTextView
    private val imageViewAdd get() = binding.imageViewAddTextView
    private val textViewTip get() = binding.textViewAddTextView

    @Dimension(unit = Dimension.DP)
    var itemAndTextGap: Int = 0
        set(@Dimension(unit = Dimension.DP) value) {
            Timber.d("setHorizontalGap() value: $value")
            flow.setHorizontalGap(value)
            field = value
        }

    @ColorInt
    var drawableTint: Int = 0
        @Deprecated(private_get_message, level = DeprecationLevel.ERROR) get() = privateGet()
        set(@ColorInt value) {
            imageViewAdd.setColorFilter(value)
            field = value
        }

    @ColorRes
    var drawableTintRes: Int = 0
        @Deprecated(private_get_message, level = DeprecationLevel.ERROR) get() = privateGet()
        set(@ColorRes value) {
            drawableTint = context.getColor(value)
            field = value
        }

    var text: String
        get() = textViewTip.text.toString()
        set(value) {
            textViewTip.text = value
        }

    @StringRes
    var textRes: Int = 0
        @Deprecated(private_get_message, level = DeprecationLevel.ERROR) get() = privateGet()
        set(@StringRes value) {
            textViewTip.setText(value)
            field = value
        }

    @StyleRes
    var textAppearanceRes: Int = 0
        @Deprecated(private_get_message, level = DeprecationLevel.ERROR) get() = privateGet()
        set(@StyleRes value) {
            textViewTip.setTextAppearance(value)
            field = value
        }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = true

    init {
        obtainStyle()
    }

    private fun obtainStyle() {
        context.obtainStyleAndRecycle(attrs, R.styleable.AddTextView) {
            itemAndTextGap = getLayoutDimension(R.styleable.AddTextView_android_drawablePadding, 0)
            drawableTint = getColor(R.styleable.AddTextView_android_drawableTint, 0)
            text = getString(R.styleable.AddTextView_android_text) ?: ""
            textAppearanceRes = getResourceId(R.styleable.AddTextView_android_textAppearance, 0)
        }
    }
}