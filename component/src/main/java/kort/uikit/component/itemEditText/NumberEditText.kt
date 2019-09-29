package kort.uikit.component.itemEditText

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.constraintlayout.helper.widget.Flow
import kort.tool.toolbox.view.*
import kort.uikit.component.R
import kort.uikit.component.databinding.NumberEdittextBinding

/**
 * Created by Kort on 2019-09-09.
 */
class NumberEditText(context: Context, attrs: AttributeSet) : BaseItemEditText(context, attrs) {
    private val binding = NumberEdittextBinding.inflate(inflater, this, true)

    override val textEditText: EditText by lazy { binding.editTextTextNumberEditText }
    override val textTextView: TextView by lazy { binding.textViewTextNumberEditText }
    override val twoText by lazy { listOf(textEditText, textTextView) }
    override val itemAndTextFlow: Flow by lazy { binding.flowNumberEditText }

    val numberTextView: TextView by lazy { binding.textViewNumberNumberEditText }

    init {
        initView()
    }

    var number: Int = 0
        set(value) {
            numberTextView.text = value.toString()
            field = value
        }

    var numberActiveTextAppearanceRes: Int = 0
        set(@StyleRes value) {
            numberTextView.setTextAppearance(value)
            field = value
        }

    var numberInactiveTextAppearanceRes: Int = 0
        set(@StyleRes value) {
            numberTextView.setTextAppearance(value)
            field = value
        }

    override var isActive: Boolean
        get() = super.isActive
        set(value) {
            val textAppearance = if (value) numberActiveTextAppearanceRes
            else numberInactiveTextAppearanceRes
            if (textAppearance != 0)
                numberTextView.setTextAppearance(textAppearance)
        }

    override fun obtainStyle(attrs: AttributeSet) {
        super.obtainStyle(attrs)
        context.obtainStyleAndRecycle(
            attrs,
            R.styleable.NumberEditText,
            R.style.UIKit_Component_NumberEditText
        ) {
            number = getInteger(R.styleable.NumberEditText_number, 0)
            numberActiveTextAppearanceRes =
                getResourceId(R.styleable.NumberEditText_numberActiveTextAppearance, 0)
            numberInactiveTextAppearanceRes =
                getResourceId(R.styleable.NumberEditText_numberInactiveTextAppearance, 0)
        }
    }
}