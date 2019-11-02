package kort.uikit.sample.list.checkboxlist

import androidx.recyclerview.widget.DiffUtil
import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.itemEditText.ChildEditItemModel
import kort.uikit.component.itemEditText.CheckableEditItemModel

/**
 * Created by Kort on 2019/9/25.
 */
data class CheckBoxEditItem(
    override var id: String = "",
    override var parentId: String = "",
    override var title: String = "",
    override var order: Int = 0,
    override var isChecked: Boolean = false
) : CheckableEditItemModel, ChildEditItemModel {
    object Diff : DiffUtil.ItemCallback<CheckBoxEditItem>() {
        override fun areItemsTheSame(
            oldItem: CheckBoxEditItem,
            newItem: CheckBoxEditItem
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CheckBoxEditItem,
            newItem: CheckBoxEditItem
        ): Boolean = oldItem == newItem
    }
}