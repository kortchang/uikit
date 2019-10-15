package kort.uikit.sample.list.checkboxlist

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edititemlist.ItemAddViewHolder
import kort.uikit.component.edititemlist.EditItemViewModelDelegate
import kort.uikit.component.edititemlist.single.SingleListViewHolder
import kort.uikit.component.edititemlist.single.SingleListWithAddAdapter
import kort.uikit.component.itemEditText.BaseItemEditText
import kort.uikit.sample.databinding.ItemAddTextViewBinding
import kort.uikit.sample.databinding.ItemCheckboxEdittextBinding

/**
 * Created by Kort on 2019/9/25.
 */
class CheckBoxListAdapter(private val viewModel: EditItemViewModelDelegate) :
    SingleListWithAddAdapter<CheckBoxEditItem, CheckBoxListAdapter.CheckBoxListViewHolder, ItemAddViewHolder>(
        CheckBoxListViewHolder::class, ItemAddViewHolder::class
    ) {
    override fun createNormalViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): CheckBoxListViewHolder {
        val binding = ItemCheckboxEdittextBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return CheckBoxListViewHolder(binding)
    }

    override fun createAddViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemAddViewHolder {
        val binding = ItemAddTextViewBinding.inflate(inflater, parent, false).apply {
            text = "新增待辦事項"
        }
        return ItemAddViewHolder(binding.root) { viewModel.addNewItemAtLast() }
    }

    inner class CheckBoxListViewHolder(private val binding: ItemCheckboxEdittextBinding) :
        SingleListViewHolder<CheckBoxEditItem>(binding.root) {
        override val itemEditText: BaseItemEditText = binding.checkboxEditText
        override fun bind(item: CheckBoxEditItem) {
            binding.checkboxEditText.hint = "輸入待辦事項"
            binding.executeAfter {
                position = adapterPosition
                this.item = item
            }
        }
    }
}