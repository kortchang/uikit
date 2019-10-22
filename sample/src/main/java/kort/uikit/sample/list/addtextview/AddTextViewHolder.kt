package kort.uikit.sample.list.addtextview

import androidx.recyclerview.widget.RecyclerView
import kort.uikit.component.edititemlist.EditItemViewModelDelegate
import kort.uikit.sample.databinding.ItemAddTextViewBinding

/**
 * Created by Kort on 2019/10/22.
 */
class AddTextViewHolder(
    binding: ItemAddTextViewBinding,
    viewModel: EditItemViewModelDelegate
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { viewModel.addNewItemAtLast() }
    }
}