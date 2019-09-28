package kort.uikit.sample.nestedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.edititemrecyclerview.EditItemFragment
import kort.uikit.component.edititemrecyclerview.EditItemListViewModelDelegateInterface
import kort.uikit.component.edititemrecyclerview.ItemModel
import kort.uikit.sample.databinding.NestedListFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class NestedListFragment : EditItemFragment<ItemModel, BaseViewHolder>() {
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
