package kort.uikit.sample.list.nestedlist

import androidx.recyclerview.widget.DiffUtil
import kort.uikit.component.edittextlist.EditItemModel

/**
 * Created by Kort on 2019/9/28.
 */
data class ParentEditItem(
    override var id: String = "",
    override var title: String = "",
    override var order: Int = 0
) : EditItemModel {
    object Diff : DiffUtil.ItemCallback<ParentEditItem>() {
        override fun areItemsTheSame(oldItem: ParentEditItem, newItem: ParentEditItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ParentEditItem, newItem: ParentEditItem): Boolean =
            oldItem == newItem
    }
}