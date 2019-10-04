package kort.uikit.sample.list.twopagenestedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edititemlist.EditItemViewModelDelegate
import kort.uikit.component.edititemlist.single.SingleListAdapter
import kort.uikit.component.edititemlist.single.SingleListViewHolder
import kort.uikit.component.itemEditText.BaseItemEditText
import kort.uikit.sample.databinding.ItemNumberEdittextBinding
import kort.uikit.sample.list.nestedlist.ParentEditItem
import kort.uikit.sample.list.numberlist.NumberEditItem

/**
 * Created by Kort on 2019/10/3.
 */
class ParentAdapter(private val viewModel: EditItemViewModelDelegate) :
    SingleListAdapter<ParentEditItem, ParentAdapter.ParentListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNumberEdittextBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return ParentListViewHolder(binding)
    }

    inner class ParentListViewHolder(private val binding: ItemNumberEdittextBinding) :
        SingleListViewHolder<ParentEditItem>(binding.root) {
        override val itemEditText: BaseItemEditText = binding.numberEditText
        override fun bind(item: ParentEditItem) {
            binding.executeAfter {
                position = adapterPosition
                this.item = item
            }
        }
    }
}