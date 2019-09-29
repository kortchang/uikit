package kort.uikit.sample.numberlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kort.uikit.component.edititemlist.EditItemListFragment
import kort.uikit.sample.databinding.FragmentNumberListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NumberListFragment :
    EditItemListFragment<NumberEditItem, NumberListAdapter.NumberListViewHolder>() {
    private lateinit var binding: FragmentNumberListBinding

    val viewModel by viewModel<NumberListViewModel>()
    override val delegate by lazy { viewModel }
    override val adapter by lazy { NumberListAdapter(delegate) }
    override val recyclerView: RecyclerView by lazy { binding.recyclerviewNumberlist }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNumberListBinding.inflate(inflater, container, false)
        return binding.root
    }
}
