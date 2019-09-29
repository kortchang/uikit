package kort.uikit.sample.checkboxlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.uikit.component.edititemlist.EditItemListFragment
import kort.uikit.sample.databinding.CheckBoxFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CheckBoxFragment :
    EditItemListFragment<CheckBoxItem, CheckBoxListAdapter.CheckBoxListViewHolder>() {
    private lateinit var binding: CheckBoxFragmentBinding
    override val delegate: CheckBoxViewModel by viewModel()
    override val adapter: BaseAdapter<CheckBoxItem, CheckBoxListAdapter.CheckBoxListViewHolder>
            by lazy { CheckBoxListAdapter(delegate) }
    override val recyclerView: RecyclerView by lazy { binding.recyclerviewCheckBox }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CheckBoxFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
