package kort.uikit.sample.list.addtextview

import androidx.recyclerview.widget.RecyclerView
import kort.uikit.component.edittextlist.EditItemDelegate
import kort.uikit.sample.databinding.ItemAddTextViewBinding

/**
 * Created by Kort on 2019/10/22.
 */
class AddTextViewHolder(
    binding: ItemAddTextViewBinding,
    viewModel: EditItemDelegate
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { viewModel.addNewItemAtLast() }
    }
}