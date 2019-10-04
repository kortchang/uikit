package kort.uikit.component.edititemlist

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.livedata.EventObserver
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import timber.log.Timber

/**
 * Created by Kort on 2019/9/24.
 */
abstract class EditItemListFragment<T : EditItemModel> :
    Fragment() {
    protected abstract val adapter: BaseAdapter<T, out BaseViewHolder>
    protected abstract val listObserver: ListEventObserverInterface
    protected abstract val recyclerView: RecyclerView
    protected abstract val listLiveData: LiveData<MutableList<T>>

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
        bindMainList()
        bindListObserver()
    }

    protected open fun bindMainList() {
        listLiveData.observe(this, Observer {
            adapter.currentList = it
            Timber.d("currentList: $it")
        })
    }

    protected open fun bindListObserver() {
        listObserver.addItemAt.observe(this, EventObserver {
            adapter.notifyItemInserted(it)
            Timber.d("addItemAt $it")
        })

        listObserver.changeItemAt.observe(this, EventObserver {
            Timber.d("changeItemAt: ${it.first} to ${it.last}")
            adapter.notifyItemRangeChanged(it.first, it.count())
        })

        listObserver.deleteItemAt.observe(this, EventObserver {
            Timber.d("deleteItemAt $it")
            adapter.notifyItemRemoved(it)
        })

        listObserver.focusItemAt.observe(this, EventObserver {
            Timber.d("focusItemAt $it")
            adapter.focusAt = it
            adapter.notifyItemChanged(it)
            recyclerView.scrollToPosition(it)
        })
    }
}