package kort.uikit.sample.list.checkboxlist

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edittextlist.EditItemDelegate
import kort.uikit.component.edittextlist.single.SingleListViewHolder
import kort.uikit.component.edittextlist.single.SingleListWithAddAdapter
import kort.uikit.component.itemEditText.BaseItemEditText
import kort.uikit.sample.databinding.ItemAddTextViewBinding
import kort.uikit.sample.databinding.ItemCheckboxEdittextBinding
import kort.uikit.sample.list.addtextview.AddTextViewHolder

/**
 * Created by Kort on 2019/9/25.
 */
class CheckBoxListAdapter(private val viewModel: EditItemDelegate) :
    SingleListWithAddAdapter<CheckBoxEditItem, CheckBoxListAdapter.CheckBoxListViewHolder, AddTextViewHolder>(
        CheckBoxListViewHolder::class, AddTextViewHolder::class, CheckBoxEditItem.Diff
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
    ): AddTextViewHolder {
        val binding = ItemAddTextViewBinding.inflate(inflater, parent, false).apply {
            text = "新增待辦事項"
        }
        return AddTextViewHolder(binding, viewModel)
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