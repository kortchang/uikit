package kort.uikit.component.edititemlist.single

import android.view.View
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.edititemlist.ItemModel
import kort.uikit.component.itemEditText.BaseItemEditText

/**
 * Created by Kort on 2019/9/23.
 */
abstract class SingleListAdapter<T : ItemModel, VH : SingleListViewHolder<T>> : BaseAdapter<T, VH>() {
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
        if (position == focusAt) focus(holder)
    }

    private fun focus(holder: VH) {
        holder.focus()
        focusAt = null
    }
}

abstract class SingleListViewHolder<T : ItemModel>(view: View) : BaseViewHolder(view) {
    abstract val itemEditText: BaseItemEditText
    abstract fun bind(item: T)
    override fun focus() {
        itemEditText.focus()
    }
}