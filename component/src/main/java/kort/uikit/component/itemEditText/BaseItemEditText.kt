package kort.uikit.component.itemEditText

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.Dimension
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.jakewharton.rxbinding3.view.keys
import com.jakewharton.rxbinding3.widget.editorActionEvents
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import kort.tool.toolbox.view.obtainStyleAndRecycle
import kort.tool.toolbox.view.privateGet
import kort.uikit.component.R
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kort.tool.toolbox.view.private_get_message


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

    private var mOnWrapLineListener: EditItemListener.OnWrapLineListener? = null
    private var mOnDeleteListener: EditItemListener.OnDeleteListener? = null
    private var mOnTextChangeListener: EditItemListener.OnTextChange? = null

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

    var editable: Editable?
        get() = textEditText.text
        set(value) {
            textEditText.text = value
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
            textEditText.hint = value
        }


    open var isActive: Boolean = false

    var onWrapLineListener: EditItemListener.OnWrapLineListener?
        @Deprecated(private_get_message, level = DeprecationLevel.ERROR) get() = privateGet()
        set(value) {
            mOnWrapLineListener = value
        }

    var onDeleteListener: EditItemListener.OnDeleteListener?
        @Deprecated(private_get_message, level = DeprecationLevel.ERROR) get() = privateGet()
        set(value) {
            mOnDeleteListener = value
        }

    var onTextChangeListener: EditItemListener.OnTextChange?
        @Deprecated(private_get_message, level = DeprecationLevel.ERROR) get() = privateGet()
        set(value) {
            mOnTextChangeListener = value
        }

    fun focus() {
        textEditText.requestFocus()
        val length = text.length
        Timber.d("focus length: $length")
        textEditText.setSelection(length)
        val imm = context.getSystemService(InputMethodManager::class.java)
        if (imm?.isActive == false) {
            imm.showSoftInput(textEditText, SHOW_IMPLICIT)
        }
    }

    open fun initView() {
        textEditText.onCreateInputConnection(EditorInfo())
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
        }
    }

    private fun setupListener() {
        addWrapLineListener()
        addDeleteListener()
        addonTextChangeListener()
    }

    private fun addonTextChangeListener() {
        disposable.add(
            textEditText
                .textChanges()
                .skipInitialValue()
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .subscribe {
                    mOnTextChangeListener?.onTextChange(it.toString())
                }
        )
    }

    private fun addWrapLineListener() {
        var eventTime: Long = 0
        disposable.add(
            textEditText.editorActionEvents {
                if (it.actionId == KeyEvent.ACTION_DOWN) {
                    if (it.keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER) {
                        val itEventTime = it.keyEvent?.eventTime ?: 0
                        filterRepeatInShortTime(eventTime, itEventTime, 5) {
                            eventTime = itEventTime
                            whenClickEnter()
                        }
                    } else false
                } else false
            }.throttleFirst(100, TimeUnit.MILLISECONDS).subscribe()
        )
    }

    private fun filterRepeatInShortTime(
        eventTime: Long,
        newEventTime: Long,
        range: Int,
        block: () -> Unit
    ): Boolean =
        if (eventTime !in newEventTime - range..newEventTime + range) {
            block()
            true
        } else false

    private fun addDeleteListener() {
        val handle: (KeyEvent) -> Boolean = {
            if (it.action == KeyEvent.ACTION_DOWN) {
                if (text.isBlank()) when {
                    it.keyCode == KeyEvent.KEYCODE_DEL -> {
                        mOnDeleteListener?.onDelete()
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
        val wrapLineIndex = textEditText.selectionStart
        val beforeWrapLineText = text.substring(0 until wrapLineIndex)
        val afterWrapLineText =
            text.substring(wrapLineIndex..text.lastIndex)
        Timber.d("onWrapLineListener is null: ${mOnWrapLineListener == null}")
        Timber.d("onWrapLineListener: $mOnWrapLineListener")
        mOnWrapLineListener?.onWrapLine(beforeWrapLineText, afterWrapLineText)
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