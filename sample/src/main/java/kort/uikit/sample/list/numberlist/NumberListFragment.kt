package kort.uikit.sample.list.numberlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kort.uikit.component.edititemlist.EditItemListFragment
import kort.uikit.component.edititemlist.ListEventObserverInterface
import kort.uikit.sample.databinding.FragmentNumberListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NumberListFragment :
    EditItemListFragment<NumberEditItem>() {
    private lateinit var binding: FragmentNumberListBinding

    private val viewModel by viewModel<NumberListViewModel>()
    override val adapter by lazy { NumberListAdapter(viewModel) }
    override val recyclerView: RecyclerView get() = binding.recyclerviewNumberlist
    override val listObserver: ListEventObserverInterface get() = viewModel
    override val listLiveData: LiveData<MutableList<NumberEditItem>> get() = viewModel.list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNumberListBinding.inflate(inflater, container, false)
        return binding.root
    }
}
