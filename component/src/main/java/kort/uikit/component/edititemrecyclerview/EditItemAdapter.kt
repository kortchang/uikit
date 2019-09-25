package kort.uikit.component.edititemrecyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.itemEditText.BaseItemEditText

/**
 * Created by Kort on 2019/9/23.
 */
abstract class EditItemAdapter<T : ItemModel, DB : ViewBinding, VH : EditItemViewHolder<T, DB>> :
    BaseAdapter<T, VH>() {
    var focusAt: Int? = null

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        super.onBindViewHolder(holder, position)
        if (position == focusAt) focus(holder)
    }

    private fun focus(holder: VH) {
        holder.focus()
        focusAt = null
    }
}

abstract class EditItemViewHolder<T : ItemModel, DB : ViewBinding>(private val binding: DB) :
    BaseViewHolder<T>(binding) {
    abstract val itemEditText: BaseItemEditText
    fun focus() {
        itemEditText.focus()
    }
}