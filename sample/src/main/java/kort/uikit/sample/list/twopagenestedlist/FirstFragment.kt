package kort.uikit.sample.list.twopagenestedlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kort.uikit.component.edititemlist.EditItemListFragment
import kort.uikit.component.edititemlist.ListEventObserverInterface
import kort.uikit.sample.R

import kort.uikit.sample.databinding.FragmentFirstBinding
import kort.uikit.sample.list.nestedlist.ParentEditItem
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FirstFragment : EditItemListFragment<ParentEditItem>() {
    lateinit var binding: FragmentFirstBinding
    private val hostFragment by lazy { requireParentFragment().requireParentFragment() }
    private val viewModel: FirstViewModel by viewModel()
    private val sharedViewModel: TwoPageNestedListViewModel
            by sharedViewModel(from = { hostFragment }, parameters = { parametersOf(viewModel) })

    override val adapter by lazy { FirstAdapter(viewModel) }
    override val recyclerView: RecyclerView get() = binding.recyclerviewFirst
    override val listObserver: ListEventObserverInterface get() = viewModel
    override val listLiveData: LiveData<MutableList<ParentEditItem>> get() = viewModel.list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.button2.setOnClickListener {
            passDataToSharedViewModel()
            findNavController().navigate(R.id.secondFragment)
        }
    }

    private fun passDataToSharedViewModel() {
        sharedViewModel.setParentList(viewModel.list.value ?: mutableListOf())
    }
}
