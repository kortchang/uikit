package kort.uikit.component.edititemlist

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.livedata.EventObserver
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegateInterface
import timber.log.Timber

/**
 * Created by Kort on 2019/9/24.
 */
abstract class EditItemListFragment<T : ItemModel, ITEM_VH : BaseViewHolder> :
    Fragment() {
    protected abstract val adapter: BaseAdapter<T, ITEM_VH>
    protected abstract val delegate: SingleListViewModelDelegateInterface<T>
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
        delegate.list.observe(this, Observer {
            adapter.currentList = it
            Timber.d("currentList : $it")
        })

        delegate.addItemAt.observe(this, EventObserver {
            adapter.notifyItemInserted(it)
            Timber.d("addItemAt $it")
        })

        delegate.changeItemAt.observe(this, EventObserver {
            Timber.d("changeItemAt: ${it.first} to ${it.last}")
            adapter.notifyItemRangeChanged(it.first, it.count())
        })

        delegate.deleteItemAt.observe(this, EventObserver {
            Timber.d("deleteItemAt $it")
            adapter.notifyItemRemoved(it)
        })

        delegate.focusItemAt.observe(this, EventObserver {
            Timber.d("focusItemAt $it")
            adapter.focusAt = it
            adapter.notifyItemChanged(it)
            recyclerView.scrollToPosition(it)
        })
    }
}