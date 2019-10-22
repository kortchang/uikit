package kort.uikit.sample.list.twopagenestedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edititemlist.EditItemViewModelDelegate
import kort.uikit.component.edititemlist.single.SingleListViewHolder
import kort.uikit.component.edititemlist.single.SingleListWithAddAdapter
import kort.uikit.component.itemEditText.BaseItemEditText
import kort.uikit.sample.databinding.ItemAddTextViewBinding
import kort.uikit.sample.databinding.ItemNumberEdittextBinding
import kort.uikit.sample.list.addtextview.AddTextViewHolder
import kort.uikit.sample.list.nestedlist.ParentEditItem

/**
 * Created by Kort on 2019/10/3.
 */
class FirstAdapter(private val viewModel: EditItemViewModelDelegate) :
    SingleListWithAddAdapter<ParentEditItem, FirstAdapter.ParentListViewHolder, AddTextViewHolder>(
        ParentListViewHolder::class, AddTextViewHolder::class
    ) {
    override fun createNormalViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ParentListViewHolder {
        val binding = ItemNumberEdittextBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return ParentListViewHolder(binding)
    }

    override fun createAddViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): AddTextViewHolder {
        val binding = ItemAddTextViewBinding.inflate(inflater, parent, false).apply {
            text = "新增行動清單"
        }
        return AddTextViewHolder(binding, viewModel)
    }

    inner class ParentListViewHolder(private val binding: ItemNumberEdittextBinding) :
        SingleListViewHolder<ParentEditItem>(binding.root) {
        override val itemEditText: BaseItemEditText = binding.numberEditText
        override fun bind(item: ParentEditItem) {
            binding.executeAfter {
                number = adapterPosition + 1
                position = adapterPosition
                this.item = item
            }
        }
    }
}