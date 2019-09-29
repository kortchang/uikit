package kort.uikit.sample.nestedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.edititemlist.EditItemListFragment
import kort.uikit.component.edititemlist.single.EditItemListViewModelDelegateInterface
import kort.uikit.component.edititemlist.ItemModel
import kort.uikit.sample.databinding.NestedListFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NestedListFragment : EditItemListFragment<ItemModel, BaseViewHolder>() {
    private lateinit var binding: NestedListFragmentBinding
    private val viewModel: NestedListViewModelInterface by viewModel()
    override val delegate: EditItemListViewModelDelegateInterface<ItemModel> by lazy { viewModel }
    override val adapter: BaseAdapter<ItemModel, BaseViewHolder> by lazy { NestedAdapter(viewModel) }
    override val recyclerView: RecyclerView by lazy { binding.recyclerviewNestedList }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NestedListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun bindViewModel() {
        super.bindViewModel()
    }
}
