package kort.uikit.sample.list.numberlist

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

/**
 * Created by Kort on 2019/9/16.
 */
class NumberListAdapter(
    private val viewModel: EditItemViewModelDelegate
) : SingleListWithAddAdapter<NumberEditItem, NumberListAdapter.NumberListViewHolder, AddTextViewHolder>(
    NumberListViewHolder::class, AddTextViewHolder::class
) {
    override fun createNormalViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): NumberListViewHolder {
        val binding = ItemNumberEdittextBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return NumberListViewHolder(binding)
    }

    override fun createAddViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): AddTextViewHolder {
        val binding = ItemAddTextViewBinding.inflate(inflater, parent, false).apply {
            text = "新增行動清單"
            this.viewModel = viewModel
        }
        return AddTextViewHolder(binding)
    }

    inner class NumberListViewHolder(private val binding: ItemNumberEdittextBinding) :
        SingleListViewHolder<NumberEditItem>(binding.root) {
        override val itemEditText: BaseItemEditText = binding.numberEditText
        override fun bind(item: NumberEditItem) {
            binding.executeAfter {
                number = adapterPosition + 1
                position = adapterPosition
                this.item = item
            }
        }
    }
}