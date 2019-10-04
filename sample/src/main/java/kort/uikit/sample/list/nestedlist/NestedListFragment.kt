package kort.uikit.sample.list.nestedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.edititemlist.EditItemListFragment
import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.edititemlist.ListEventObserverInterface
import kort.uikit.component.edititemlist.ListEventSenderObserver
import kort.uikit.sample.databinding.NestedListFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NestedListFragment : EditItemListFragment<EditItemModel>() {
    private lateinit var binding: NestedListFragmentBinding
    private val viewModel: NestedListViewModel by viewModel()
    override val adapter: BaseAdapter<EditItemModel, BaseViewHolder> by lazy {
        NestedAdapter(viewModel)
    }
    override val recyclerView: RecyclerView by lazy { binding.recyclerviewNestedList }
    override val listObserver: ListEventObserverInterface get() = ListEventSenderObserver()
    override val listLiveData: LiveData<MutableList<EditItemModel>> get() = viewModel.list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NestedListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
