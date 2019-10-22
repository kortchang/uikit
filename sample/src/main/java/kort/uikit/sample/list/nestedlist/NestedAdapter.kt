package kort.uikit.sample.list.nestedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edititemlist.EditItemViewModelDelegate
import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.edititemlist.EditItemViewHolder
import kort.uikit.component.edititemlist.nested.NestedListWithAddAdapter
import kort.uikit.sample.databinding.ItemAddTextViewBinding
import kort.uikit.sample.databinding.ItemCheckboxEdittextBinding
import kort.uikit.sample.databinding.ItemNumberEdittextBinding
import kort.uikit.sample.list.addtextview.AddTextViewHolder

/**
 * Created by Kort on 2019/9/25.
 */
class NestedAdapter(private val viewModel: EditItemViewModelDelegate) :
    NestedListWithAddAdapter<ParentEditItem, ChildEditItem, EditItemModel, RecyclerView.ViewHolder>(
        ParentEditItem::class,
        ChildEditItem::class
    ) {
    override fun createParentViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): EditItemViewHolder {
        val binding = ItemNumberEdittextBinding.inflate(inflater, parent, false).apply {
            listener = viewModel
        }
        return ParentViewHolder(binding)
    }

    override fun createChildViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): EditItemViewHolder {
        val binding = ItemCheckboxEdittextBinding.inflate(inflater, parent, false).apply {
            checkboxEditText.isCheckable = false
            listener = viewModel
        }
        return ChildViewHolder(binding)
    }

    override fun createAddViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val binding = ItemAddTextViewBinding.inflate(inflater, parent, false).apply {
            text = "新增行動清單"
        }
        return AddTextViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ParentViewHolder -> {
                val item = getItem(position)
                holder.bind(item as ParentEditItem)
                focusAt(position, holder)
            }
            is ChildViewHolder -> {
                val item = getItem(position)
                holder.bind(item as ChildEditItem)
                focusAt(position, holder)
            }
        }
    }

    inner class ParentViewHolder(private val binding: ItemNumberEdittextBinding) :
        EditItemViewHolder(binding.root) {
        fun bind(item: ParentEditItem) {
            binding.executeAfter {
                this.item = item
                position = adapterPosition
                number = item.order + 1
            }
        }

        override fun focus() {
            binding.numberEditText.focus()
        }
    }

    inner class ChildViewHolder(private val binding: ItemCheckboxEdittextBinding) :
        EditItemViewHolder(binding.root) {
        fun bind(item: ChildEditItem) {
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