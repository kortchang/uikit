package kort.uikit.sample.list.checkboxlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.uikit.component.edititemlist.EditItemAdapter
import kort.uikit.component.edititemlist.EditItemListFragment
import kort.uikit.component.edititemlist.ListEventObserverInterface
import kort.uikit.sample.databinding.CheckBoxFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CheckBoxFragment :
    EditItemListFragment<CheckBoxEditItem>() {
    private lateinit var binding: CheckBoxFragmentBinding
    private val viewModel: CheckBoxViewModel by viewModel()

    override val adapter by lazy { CheckBoxListAdapter(viewModel) }
    override val recyclerView: RecyclerView by lazy { binding.recyclerviewCheckBox }
    override val listObserver: ListEventObserverInterface get() = viewModel
    override val listLiveData: LiveData<MutableList<CheckBoxEditItem>> get() = viewModel.list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CheckBoxFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
