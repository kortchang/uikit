package kort.uikit.sample.list.nestedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.databinding.executeAfter
import kort.uikit.component.edittextlist.EditItemDelegate
import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.edittextlist.EditItemViewHolder
import kort.uikit.component.edittextlist.nested.NestedListWithAddAdapter
import kort.uikit.component.itemEditText.ChildEditItemModel
import kort.uikit.sample.databinding.ItemAddTextViewBinding
import kort.uikit.sample.databinding.ItemCheckboxEdittextBinding
import kort.uikit.sample.databinding.ItemNumberEdittextBinding
import kort.uikit.sample.list.addtextview.AddTextViewHolder
import kotlin.reflect.KClass

/**
 * Created by Kort on 2019/9/25.
 */
@Suppress("UNCHECKED_CAST")
class NestedDiffUtil : DiffUtil.ItemCallback<EditItemModel>() {
    override fun areItemsTheSame(oldItem: EditItemModel, newItem: EditItemModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: EditItemModel, newItem: EditItemModel): Boolean =
        if (oldItem is ParentEditItem && newItem is ParentEditItem) {
            oldItem as ParentEditItem == newItem as ParentEditItem
        } else if (oldItem is ChildEditItem && newItem is ChildEditItem) {
            oldItem as ChildEditItem == newItem as ChildEditItem
        } else {
            false
        }
}

class NestedAdapter(private val viewModel: EditItemDelegate) :
    NestedListWithAddAdapter<ParentEditItem, ChildEditItem, EditItemModel, RecyclerView.ViewHolder>(
        ParentEditItem::class,
        ChildEditItem::class,
        NestedDiffUtil()
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