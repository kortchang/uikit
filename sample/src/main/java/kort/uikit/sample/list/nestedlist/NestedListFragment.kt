package kort.uikit.sample.list.nestedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kort.uikit.component.edititemlist.*
import kort.uikit.sample.databinding.NestedListFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NestedListFragment : EditItemListFragment<EditItemModel>() {
    private lateinit var binding: NestedListFragmentBinding
    private val viewModel: NestedListViewModel by viewModel()

    override val adapter by lazy { NestedAdapter(viewModel) }
    override val recyclerView: RecyclerView by lazy { binding.recyclerviewNestedList }
    override val listObserver: ListEventObserverInterface get() = viewModel
    override val listLiveData: LiveData<MutableList<EditItemModel>> get() = viewModel.list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NestedListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
