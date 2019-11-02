package kort.uikit.sample.list.numberlist

import androidx.recyclerview.widget.DiffUtil
import kort.uikit.component.edittextlist.EditItemModel
import timber.log.Timber

/**
 * Created by Kort on 2019/9/16.
 */
data class NumberEditItem(
    override var id: String = "",
    override var title: String = "",
    override var order: Int = 0
) : EditItemModel {
    object Diff : DiffUtil.ItemCallback<NumberEditItem>() {
        override fun areItemsTheSame(oldItem: NumberEditItem, newItem: NumberEditItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NumberEditItem, newItem: NumberEditItem): Boolean {
            Timber.d("oldItem ${oldItem.title} newItem${newItem.title} is ${oldItem == newItem}")
            return oldItem == newItem
        }
    }
}