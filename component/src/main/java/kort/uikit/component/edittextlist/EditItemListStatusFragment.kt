package kort.uikit.component.edittextlist

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.DataStatus
import kort.tool.toolbox.livedata.EventObserver
import timber.log.Timber

/**
 * Created by Kort on 2019/9/24.
 */
abstract class EditItemListStatusFragment<T : EditItemModel> : Fragment() {
    protected abstract val adapter: EditItemAdapter<T, out RecyclerView.ViewHolder>
    protected abstract val listObserver: ListEventObserverInterface
    protected abstract val recyclerView: RecyclerView
    protected abstract val listLiveData: LiveData<DataStatus<MutableList<T>>>
    protected open val isShowAddViewAtLast: Boolean = true
    private var showKeyboardWhenEnterPage = true
    protected val inputMethodManager: InputMethodManager by lazy {
        requireContext().getSystemService(InputMethodManager::class.java)
    }
    protected abstract val loadingDialog: AlertDialog

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(recyclerView)
        bindViewModel()
        if (showKeyboardWhenEnterPage) showKeyboard()
    }

    protected open fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
            it.itemAnimator = null
            it.isVisible = false
            it.isVisible = true
        }
    }

    protected open fun bindViewModel() {
        bindMainList(listLiveData)
        bindListObserver(listObserver)
    }

    protected open fun bindMainList(listLiveData: LiveData<DataStatus<MutableList<T>>>) {
        listLiveData.observe(this, Observer {
            when (it) {
                is DataStatus.Loading -> loadingDialog.show()
                is DataStatus.Success -> {
                    Timber.d("currentList: ${it.result}")
                    val originListSize = adapter.currentList.size
                    adapter.currentList = it.result
                    if (originListSize == 0) adapter.notifyDataSetChanged()
                    loadingDialog.dismiss()
                }
            }
        })
    }

    protected open fun bindListObserver(listObserver: ListEventObserverInterface) {
        listObserver.addItemAt.observe(this, EventObserver {
            adapter.notifyItemRangeInserted(it.first, it.count())
            Timber.d("addItemAt $it")
        })

        listObserver.changeItemAt.observe(this, EventObserver {
            Timber.d("changeItemAt: ${it.first} to ${it.last}")
            adapter.notifyItemRangeChanged(it.first, it.count())
        })

        listObserver.deleteItemAt.observe(this, EventObserver {
            Timber.d("deleteItemAt $it")
            adapter.notifyItemRangeRemoved(it.first, it.count())
        })

        listObserver.focusItemAt.observe(this, EventObserver {
            Timber.d("focusItemAt $it")
            adapter.focusAt = it
            adapter.notifyItemChanged(it)
            recyclerView.scrollToPosition(it)
        })
    }

    protected fun showKeyboard() {
        val focusedView = requireActivity().currentFocus
        if (focusedView != null) {
            inputMethodManager.showSoftInput(focusedView, 0)
        }
    }

    protected fun closeKeyboard() {
        val focusedView = requireActivity().currentFocus
        if (inputMethodManager.isActive && focusedView != null) {
            Timber.d("imm isActive")
            inputMethodManager.hideSoftInputFromWindow(
                focusedView.windowToken,
                0
            )
        }
    }
}