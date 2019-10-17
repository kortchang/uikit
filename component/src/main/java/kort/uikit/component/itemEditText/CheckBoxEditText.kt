package kort.uikit.component.itemEditText

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.helper.widget.Flow
import androidx.core.content.res.getColorOrThrow
import kort.tool.toolbox.view.obtainStyleAndRecycle
import kort.tool.toolbox.view.privateGet
import kort.tool.toolbox.view.private_get_message
import kort.uikit.component.R
import kort.uikit.component.databinding.CheckboxEdittextBinding

/**
 * Created by Kort on 2019/9/25.
 */
class CheckBoxEditText(context: Context, attrs: AttributeSet) : BaseItemEditText(context, attrs) {
    private val binding = CheckboxEdittextBinding.inflate(inflater, this, true)
    override val textEditText: EditText get() = binding.editTextCheckboxEditText
    override val textTextView: TextView get() = binding.textViewCheckboxEditText
    override val twoText: List<TextView> by lazy { listOf(textEditText, textTextView) }
    override val itemAndTextFlow: Flow get() = binding.flowCheckboxEditText
    override val deleteButton: ImageButton get() = binding.imageButtonDeleteCheckboxEditText

    val checkbox: CheckBox get() = binding.checkBoxCheckboxEditText

    var isChecked
        get() = checkbox.isChecked
        set(value) {
            checkbox.isChecked = value
        }

    @ColorInt
    var buttonTint: Int = -1
        @Deprecated(private_get_message, level = DeprecationLevel.ERROR) get() = privateGet()
        set(@ColorInt value) {
            if (value != -1) {
                checkbox.buttonTintList = ColorStateList.valueOf(value)
                field = value
            }
        }

    init {
        initView()
    }

    override fun obtainStyle(attrs: AttributeSet) {
        super.obtainStyle(attrs)
        context.obtainStyleAndRecycle(attrs, R.styleable.CheckBoxEditText) {
            isChecked = getBoolean(R.styleable.CheckBoxEditText_android_checked, false)
            buttonTint = getColor(R.styleable.CheckBoxEditText_android_buttonTint, -1)
        }
    }
}