package kort.uikit.component.itemEditText

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.*
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.keys
import com.jakewharton.rxbinding3.widget.*
import io.reactivex.disposables.CompositeDisposable
import kort.tool.toolbox.view.obtainStyleAndRecycle
import kort.tool.toolbox.view.privateGet
import kort.tool.toolbox.view.private_get_message
import kort.uikit.component.R
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * Created by Kort on 2019/9/13.
 */
abstract class BaseItemEditText(context: Context, private val attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    protected val inflater: LayoutInflater = LayoutInflater.from(context)

    abstract val textEditText: EditText
    protected abstract val textTextView: TextView
    protected abstract val twoText: List<TextView>
    protected abstract val itemAndTextFlow: Flow
    protected abstract val deleteButton: ImageButton

    private val disposable = CompositeDisposable()

    var itemAndTextGap: Int = 0
        set(@Dimension(unit = Dimension.DP) value) {
            itemAndTextFlow.setHorizontalGap(value)
            field = value
        }

    var isEditable = true
        set(value) {
            textEditText.isVisible = value
            textTextView.isVisible = !value
            field = value
        }

    var textAppearanceRes: Int = 0
        set(@StyleRes value) {
            twoText.forEach { it.setTextAppearance(value) }
            field = value
        }

    var text: String
        get() = textEditText.text.toString()
        set(value) {
            twoText.forEach { it.text = value }
        }

    var textRes: Int = 0
        @Deprecated(private_get_message, level = DeprecationLevel.ERROR) get() = privateGet()
        set(@StringRes value) {
            twoText.forEach { it.setText(value) }
            field = value
        }

    var hint: String
        get() = textEditText.hint.toString()
        set(value) {
            Timber.d("setHint() :$value")
            textEditText.hint = value
        }

    @ColorInt
    var hintTextColor: Int = -1
        get() = textEditText.hintTextColors.defaultColor
        set(@ColorInt value) {
            if (value != -1) {
                textEditText.setHintTextColor(value)
                field = value
            }
        }

    open var isShowDeleteButton: Boolean = true

    open var isChildCannotBeTouch: Boolean = false

    open var isActive: Boolean = false

    var onWrapLineListener: ItemEditTextListener.OnWrapLineListener? = null
    var onDeleteListener: ItemEditTextListener.OnDeleteListener? = null
    var onTextChangeListener: ItemEditTextListener.OnTextChange? = null

    fun focus() {
        textEditText.requestFocus()
        val length = text.length
        Timber.d("focus length: $length")
        textEditText.setSelection(length)
        val imm = context.getSystemService(InputMethodManager::class.java)
        if (imm?.isActive(textEditText) == false) {
            imm.showSoftInput(textEditText, SHOW_IMPLICIT)
        }
    }

    open fun initView() {
        obtainStyle(attrs)
        setupListener()
    }

    @CallSuper
    open fun obtainStyle(attrs: AttributeSet) {
        context.obtainStyleAndRecycle(attrs, R.styleable.BaseItemEditText) {
            itemAndTextGap = getLayoutDimension(R.styleable.BaseItemEditText_itemAndTextGap, 0)
            text = getString(R.styleable.BaseItemEditText_android_text) ?: ""
            textAppearanceRes =
                getResourceId(R.styleable.BaseItemEditText_android_textAppearance, 0)
            hint = getString(R.styleable.BaseItemEditText_android_hint) ?: ""
            hintTextColor = getColor(R.styleable.BaseItemEditText_android_textColorHint, -1)
            isShowDeleteButton = getBoolean(R.styleable.BaseItemEditText_showDeleteButton, true)
        }
    }

    private fun setupListener() {
        addOnFocusListener()
        addDeleteListener()
        addOnTextChangeListener()
        addOnDeleteButtonClickListener()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean =
        if (isChildCannotBeTouch) true else super.onInterceptTouchEvent(ev)

    private fun addOnFocusListener() {
        textEditText.setOnFocusChangeListener { _, isOnFocus ->
            //            setupDeleteButton(isOnFocus)
//            setupHint(isOnFocus)
        }
    }

    private fun setupDeleteButton(isOnFocus: Boolean) {
        if (isShowDeleteButton) {
            deleteButton.isInvisible = !isOnFocus
        }
    }

    private fun setupHint(isOnFocus: Boolean) {
        textEditText.hint = if (isOnFocus) hint else "onFocus"
        Timber.d("text:$text isOnFocus: $isOnFocus")
        Timber.d("hint: $hint")
        Timber.d("editText hint:${textEditText.hint}")
    }

    private fun addOnDeleteButtonClickListener() {
        disposable.add(deleteButton.clicks().throttleFirst(100, TimeUnit.MILLISECONDS).subscribe {
            onDeleteListener?.onDelete()
        })
    }

    private var onWrapLineEventTime = 0L
    private var textChangeEventTime = 0L
    private fun addOnTextChangeListener() {
        disposable.add(
            textEditText
                .textChangeEvents()
                .subscribe {
                    if (it.text.contains('\n')) {
                        filterRepeatInShortTime(
                            onWrapLineEventTime,
                            System.currentTimeMillis(),
                            100
                        ) {
                            onWrapLineEventTime = System.currentTimeMillis()
                            Timber.d("text contain wrapline mark")
                            whenClickEnter()
                        }
                    } else {
                        onTextChangeListener?.onTextChange(it.text.toString())
                    }
                }
        )
    }

    private fun filterRepeatInShortTime(
        eventTime: Long,
        newEventTime: Long,
        range: Int,
        block: () -> Unit
    ): Boolean = kotlin.run {
        Timber.d("eventTime: $eventTime start: ${newEventTime - range} end: ${newEventTime + range}")
        if (eventTime !in (newEventTime - range)..(newEventTime + range)) {
            Timber.d("filter: block()")
            block()
            true
        } else false
    }

    private fun addDeleteListener() {
        val handle: (KeyEvent) -> Boolean = {
            if (it.action == KeyEvent.ACTION_DOWN) {
                if (text.isBlank()) when {
                    it.keyCode == KeyEvent.KEYCODE_DEL -> {
                        onDeleteListener?.onDelete()
                        true
                    }
                    else -> false
                } else false
            } else false
        }
        disposable.add(textEditText.keys(handle).subscribe())
    }

    private fun whenClickEnter() {
        Timber.d("clickEnter")
        val wrapLineIndexFirst = text.indexOfFirst { it == '\n' }
        val wrapLineIndexLast = text.indexOfLast { it == '\n' }
        val beforeWrapLineText = text.substring(0 until wrapLineIndexFirst)
        val afterWrapLineText = text.substring((wrapLineIndexLast + 1)..text.lastIndex)
        Timber.d("beforeWrapLineText: $beforeWrapLineText")
        Timber.d("afterWrapLineText: $afterWrapLineText")
        onWrapLineListener?.onWrapLine(beforeWrapLineText, afterWrapLineText)
    }

    private fun clearListener() {
        disposable.clear()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupListener()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearListener()
    }
}