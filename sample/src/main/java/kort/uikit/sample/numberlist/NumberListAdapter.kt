package kort.uikit.sample.numberlist

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edititemrecyclerview.EditItemAdapter
import kort.uikit.component.edititemrecyclerview.EditItemViewHolder
import kort.uikit.component.edititemrecyclerview.EditItemListViewModelDelegate
import kort.uikit.component.itemEditText.BaseItemEditText
import kort.uikit.sample.databinding.ItemNumberListBinding

/**
 * Created by Kort on 2019/9/16.
 */
class NumberListAdapter(
    private val viewModel: EditItemListViewModelDelegate<NumberItem>
) : EditItemAdapter<NumberItem, ItemNumberListBinding, NumberListAdapter.NumberListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNumberListBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return NumberListViewHolder(binding)
    }

    inner class NumberListViewHolder(private val binding: ItemNumberListBinding) :
        EditItemViewHolder<NumberItem, ItemNumberListBinding>(binding) {
        override val itemEditText: BaseItemEditText = binding.numberEditText
        override fun bind(item: NumberItem) {
            binding.executeAfter {
                position = adapterPosition
                this.item = item
            }
        }
    }
}