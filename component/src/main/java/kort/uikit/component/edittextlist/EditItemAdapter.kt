package kort.uikit.component.edittextlist

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.view.recyclerview.BaseAdapter

/**
 * Created by Kort on 2019/10/8.
 */
abstract class EditItemAdapter<T : EditItemModel, VH : RecyclerView.ViewHolder>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(diffUtil) {
    open var focusAt: Int? = null

    protected open fun focusAt(position: Int, holder: EditItemViewHolder) {
        if (position == focusAt) {
            holder.focus()
            focusAt = null
        }
    }
}

abstract class EditItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun focus() {}
}