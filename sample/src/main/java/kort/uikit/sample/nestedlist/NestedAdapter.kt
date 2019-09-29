package kort.uikit.sample.nestedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.databinding.executeAfter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.edititemlist.single.EditItemListListener
import kort.uikit.component.edititemlist.ItemModel
import kort.uikit.component.edititemlist.nested.NestedListAdapter
import kort.uikit.sample.databinding.ItemCheckboxEdittextBinding
import kort.uikit.sample.databinding.ItemNumberEdittextBinding

/**
 * Created by Kort on 2019/9/25.
 */
class NestedAdapter(private val viewModel: EditItemListListener) :
    NestedListAdapter<ParentItem, ChildItem, ItemModel, BaseViewHolder>(
        ParentItem::class,
        ChildItem::class
    ) {
    override fun buildParentViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder {
        val binding = ItemNumberEdittextBinding.inflate(inflater, parent, false)
        return ParentViewHolder(binding)
    }

    override fun buildChildViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder {
        val binding = ItemCheckboxEdittextBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ParentViewHolder -> holder.bind(item as ParentItem)
            is ChildViewHolder -> holder.bind(item as ChildItem)
        }
        focusAt(position, holder)
    }

    inner class ParentViewHolder(private val binding: ItemNumberEdittextBinding) :
        BaseViewHolder(binding.root) {
        fun bind(item: ParentItem) {
            binding.executeAfter {
                this.item = item
                position = item.order
            }
        }

        override fun focus() {
            binding.numberEditText.focus()
        }
    }

    inner class ChildViewHolder(private val binding: ItemCheckboxEdittextBinding) :
        BaseViewHolder(binding.root) {
        fun bind(item: ChildItem) {
            binding.executeAfter {
                this.item = item
                position = adapterPosition
            }
        }

        override fun focus() {
            binding.checkboxEditText.focus()
        }
    }
}