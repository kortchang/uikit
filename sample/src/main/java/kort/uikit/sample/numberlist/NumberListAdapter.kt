package kort.uikit.sample.numberlist

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edititemrecyclerview.EditItemAdapter
import kort.uikit.component.edititemrecyclerview.EditItemViewHolder
import kort.uikit.component.edititemrecyclerview.EditItemListViewModelDelegateInterface
import kort.uikit.component.itemEditText.BaseItemEditText
import kort.uikit.sample.databinding.ItemNumberEdittextBinding

/**
 * Created by Kort on 2019/9/16.
 */
class NumberListAdapter(
    private val viewModel: EditItemListViewModelDelegateInterface<NumberItem>
) : EditItemAdapter<NumberItem, NumberListAdapter.NumberListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNumberEdittextBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return NumberListViewHolder(binding)
    }

    inner class NumberListViewHolder(private val binding: ItemNumberEdittextBinding) :
        EditItemViewHolder<NumberItem>(binding.root) {
        override val itemEditText: BaseItemEditText = binding.numberEditText
        override fun bind(item: NumberItem) {
            binding.executeAfter {
                position = adapterPosition
                this.item = item
            }
        }
    }
}