package kort.uikit.component.edititemlist.single

import android.view.View
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.itemEditText.BaseItemEditText

/**
 * Created by Kort on 2019/9/23.
 */
abstract class SingleListAdapter<T : EditItemModel, VH : SingleListViewHolder<T>> :
    BaseAdapter<T, VH>() {
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
        focusAt(position, holder)
    }
}

abstract class SingleListViewHolder<T : EditItemModel>(view: View) : BaseViewHolder(view) {
    abstract val itemEditText: BaseItemEditText
    abstract fun bind(item: T)
    override fun focus() {
        itemEditText.focus()
    }
}