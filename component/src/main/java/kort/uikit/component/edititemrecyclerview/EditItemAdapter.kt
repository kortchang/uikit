package kort.uikit.component.edititemrecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.itemEditText.BaseItemEditText

/**
 * Created by Kort on 2019/9/23.
 */
abstract class EditItemAdapter<T : ItemModel, VH : EditItemViewHolder<T>> : BaseAdapter<T, VH>() {
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
        if (position == focusAt) focus(holder)
    }

    private fun focus(holder: VH) {
        holder.focus()
        focusAt = null
    }
}

abstract class EditItemViewHolder<T : ItemModel>(view: View) : BaseViewHolder(view) {
    abstract val itemEditText: BaseItemEditText
    abstract fun bind(item: T)
    override fun focus() {
        itemEditText.focus()
    }
}