package kort.uikit.sample.checkboxlist

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edititemlist.single.SingleListAdapter
import kort.uikit.component.edititemlist.single.SingleListViewHolder
import kort.uikit.component.itemEditText.BaseItemEditText
import kort.uikit.sample.databinding.ItemCheckboxEdittextBinding

/**
 * Created by Kort on 2019/9/25.
 */
class CheckBoxListAdapter(private val viewModel: CheckBoxViewModel) :
    SingleListAdapter<CheckBoxItem, CheckBoxListAdapter.CheckBoxListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckBoxListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCheckboxEdittextBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return CheckBoxListViewHolder(binding)
    }

    inner class CheckBoxListViewHolder(private val binding: ItemCheckboxEdittextBinding) :
        SingleListViewHolder<CheckBoxItem>(binding.root) {
        override val itemEditText: BaseItemEditText = binding.checkboxEditText

        override fun bind(item: CheckBoxItem) {
            binding.executeAfter {
                position = adapterPosition
                this.item = item
            }
        }
    }
}