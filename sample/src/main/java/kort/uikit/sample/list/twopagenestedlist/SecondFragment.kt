package kort.uikit.sample.list.twopagenestedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.edititemlist.EditItemListFragment
import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.edititemlist.ListEventObserverInterface
import kort.uikit.sample.databinding.FragmentSecondBinding
import kort.uikit.sample.list.nestedlist.NestedAdapter
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * Created by Kort on 2019/10/4.
 */
class SecondFragment : EditItemListFragment<EditItemModel>() {
    lateinit var binding: FragmentSecondBinding
    val trueParentFragment by lazy { requireParentFragment().requireParentFragment() }
    val viewModel: TwoPageNestedListViewModel by sharedViewModel(from = { trueParentFragment })
    override val adapter: BaseAdapter<EditItemModel, out BaseViewHolder> by lazy {
        NestedAdapter(viewModel)
    }
    override val listObserver: ListEventObserverInterface
        get() = viewModel
    override val recyclerView: RecyclerView
        get() = binding.recyclerviewSecond
    override val listLiveData: LiveData<MutableList<EditItemModel>>
        get() = viewModel.list

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }
}