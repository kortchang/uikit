package kort.uikit.component.edittextlist

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.core.view.doOnLayout
import androidx.core.view.doOnNextLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.livedata.EventObserver
import kort.tool.toolbox.livedata.eventObserve
import timber.log.Timber

/**
 * Created by Kort on 2019/9/24.
 */
abstract class EditItemListFragment<T : EditItemModel> : Fragment() {
    protected abstract val adapter: EditItemAdapter<T, out RecyclerView.ViewHolder>
    protected abstract val listObserver: ListEventObserverInterface
    protected abstract val recyclerView: RecyclerView
    protected abstract val listLiveData: LiveData<MutableList<T>>
    protected open val isShowAddViewAtLast: Boolean = true
    protected open var showKeyboardWhenEnterPage = true
    protected val inputMethodManager: InputMethodManager by lazy {
        requireContext().getSystemService(InputMethodManager::class.java)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(recyclerView)
        bindViewModel()
        showKeyboard()
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

    protected open fun bindMainList(listLiveData: LiveData<MutableList<T>>) {
        listLiveData.observe(this, Observer {
            adapter.submitList(it)
            Timber.d("currentList: $it")
        })
    }

    protected open fun bindListObserver(listObserver: ListEventObserverInterface) {
//        listObserver.addItemAt.observe(this, EventObserver {
//            adapter.notifyItemRangeInserted(it.first, it.count())
//            Timber.d("addItemAt $it")
//        })
//
        listObserver.changeItemAt.eventObserve(this) {
            Timber.d("changeItemAt: ${it.first} to ${it.last}")
            if (!recyclerView.isComputingLayout) {
                adapter.notifyItemRangeChanged(it.first, it.count())
                if (isShowAddViewAtLast) {
                    adapter.notifyItemChanged(adapter.itemCount - 1)
                }
            }
        }
//
//        listObserver.deleteItemAt.observe(this, EventObserver {
//            Timber.d("deleteItemAt $it")
//            adapter.notifyItemRangeRemoved(it.first, it.count())
//        })

        listObserver.focusItemAt.eventObserve(this) {
            Timber.d("focusItemAt $it")
            adapter.focusAt = it
            adapter.notifyItemChanged(it)
            recyclerView.scrollToPosition(it)
        }
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
            inputMethodManager.hideSoftInputFromWindow(
                focusedView.windowToken,
                0
            )
        }
    }
}