package kort.uikit.sample.checkboxlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import kort.uikit.component.edititemrecyclerview.EditItemAdapter
import kort.uikit.component.edititemrecyclerview.EditItemFragment
import kort.uikit.sample.databinding.CheckBoxFragmentBinding
import kort.uikit.sample.databinding.ItemCheckboxEdittextBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CheckBoxFragment :
    EditItemFragment<CheckBoxItem, ItemCheckboxEdittextBinding, CheckBoxListAdapter.CheckBoxListViewHolder>() {
    private lateinit var binding: CheckBoxFragmentBinding
    override val viewModel: CheckBoxViewModel by viewModel()
    override val adapter: EditItemAdapter<CheckBoxItem, ItemCheckboxEdittextBinding, CheckBoxListAdapter.CheckBoxListViewHolder> by lazy {
        CheckBoxListAdapter(viewModel)
    }
    override val recyclerView: RecyclerView by lazy { binding.recyclerviewCheckBox }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CheckBoxFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
