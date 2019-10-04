package kort.uikit.sample.list.numberlist

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edititemlist.EditItemViewModelDelegate
import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.edititemlist.single.SingleListAdapter
import kort.uikit.component.edititemlist.single.SingleListViewHolder
import kort.uikit.component.itemEditText.BaseItemEditText
import kort.uikit.sample.databinding.ItemNumberEdittextBinding

/**
 * Created by Kort on 2019/9/16.
 */
class NumberListAdapter(
    private val viewModel: EditItemViewModelDelegate
) : SingleListAdapter<NumberEditItem, NumberListAdapter.NumberListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNumberEdittextBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return NumberListViewHolder(binding)
    }

    inner class NumberListViewHolder(private val binding: ItemNumberEdittextBinding) :
        SingleListViewHolder<NumberEditItem>(binding.root) {
        override val itemEditText: BaseItemEditText = binding.numberEditText
        override fun bind(item: NumberEditItem) {
            binding.executeAfter {
                position = adapterPosition
                this.item = item
            }
        }
    }
}