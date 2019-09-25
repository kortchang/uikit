package kort.uikit.component.edititemrecyclerview

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kort.tool.toolbox.livedata.EventObserver
import timber.log.Timber

/**
 * Created by Kort on 2019/9/24.
 */
abstract class EditItemFragment<T : ItemModel, ITEM_VB : ViewBinding, ITEM_VH : EditItemViewHolder<T, ITEM_VB>> :
    Fragment() {
    protected abstract val adapter: EditItemAdapter<T, ITEM_VB, ITEM_VH>
    protected abstract val viewModel: EditItemListViewModelDelegate<T>
    protected abstract val recyclerView: RecyclerView

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        bindViewModel()
    }

    protected open fun setupRecyclerView() {
        recyclerView.also {
            it.adapter = adapter
            it.itemAnimator = null
        }
    }

    protected open fun bindViewModel() {
        viewModel.list.observe(this, Observer {
            adapter.currentList = it
        })

        viewModel.addItemAt.observe(this, EventObserver {
            adapter.notifyItemInserted(it)
            Timber.d("addItemAt $it")
        })

        viewModel.changeItemAt.observe(this, EventObserver {
            Timber.d("changeItemAt: ${it.first} to ${it.last}")
            adapter.notifyItemRangeChanged(it.first, it.count())
        })

        viewModel.deleteItemAt.observe(this, EventObserver {
            Timber.d("deleteItemAt $it")
            adapter.notifyItemRemoved(it)
        })

        viewModel.focusItemAt.observe(this, EventObserver {
            Timber.d("focusItemAt $it")
            adapter.focusAt = it.focusAt
            adapter.notifyItemChanged(it.focusAt)
            recyclerView.scrollToPosition(it.focusAt)
        })
    }
}