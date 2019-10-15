package kort.uikit.sample.list.twopagenestedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.uikit.component.edititemlist.*
import kort.uikit.sample.databinding.FragmentSecondBinding
import kort.uikit.sample.list.nestedlist.NestedAdapter
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * Created by Kort on 2019/10/4.
 */
class SecondFragment : EditItemListFragment<EditItemModel>() {
    lateinit var binding: FragmentSecondBinding
    private val trueParentFragment by lazy { requireParentFragment().requireParentFragment() }
    private val viewModel: TwoPageNestedListViewModel by sharedViewModel(from = { trueParentFragment })
    override val adapter by lazy { NestedAdapter(viewModel) }
    override val listObserver: ListEventObserverInterface get() = viewModel
    override val listLiveData: LiveData<MutableList<EditItemModel>> get() = viewModel.list
    override val recyclerView: RecyclerView get() = binding.recyclerviewSecond

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }
}