package kort.uikit.component.edititemlist.single

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kort.uikit.component.edititemlist.EditItemAdapter
import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.edititemlist.EditItemViewHolder
import kort.uikit.component.itemEditText.BaseItemEditText
import kotlin.reflect.KClass

/**
 * Created by Kort on 2019/9/23.
 */
abstract class SingleListAdapter<T : EditItemModel, VH : SingleListViewHolder<T>> :
    EditItemAdapter<T, VH>() {
    override fun onBindViewHolder(holder: VH, position: Int) {
        bindEditItem(position, holder)
    }

    protected open fun bindEditItem(position: Int, holder: VH) {
        holder.bind(getItem(position))
        focusAt(position, holder)
    }
}

abstract class SingleListViewHolder<T : EditItemModel>(view: View) : EditItemViewHolder(view) {
    abstract val itemEditText: BaseItemEditText
    abstract fun bind(item: T)
    override fun focus() {
        itemEditText.focus()
    }
}

abstract class SingleListWithAddAdapter<T : EditItemModel, VH : SingleListViewHolder<T>, ADDVH : RecyclerView.ViewHolder>(
    private val viewHolderClass: KClass<VH>,
    private val addViewHolderClass: KClass<ADDVH>
) : EditItemAdapter<T, RecyclerView.ViewHolder>() {
    companion object {
        const val NORMAL_VIEW_TYPE = 0
        const val ADD_VIEW_TYPE = 1
    }

    override fun getItemViewType(position: Int): Int =
        if (itemCount - 1 == position) ADD_VIEW_TYPE else NORMAL_VIEW_TYPE

    override fun getItemCount(): Int = super.getItemCount() + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            NORMAL_VIEW_TYPE -> createNormalViewHolder(inflater, parent)
            ADD_VIEW_TYPE -> createAddViewHolder(inflater, parent)
            else -> throw Exception("onCreateViewHolder(): The viewType: $viewType is not match the view type option.")
        }
    }

    abstract fun createNormalViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH
    abstract fun createAddViewHolder(inflater: LayoutInflater, parent: ViewGroup): ADDVH

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            viewHolderClass.isInstance(holder) -> bindEditItem(position, holder as VH)
            addViewHolderClass.isInstance(holder) -> bindAddTextView(holder as ADDVH)
        }
    }

    protected open fun bindEditItem(position: Int, holder: VH) {
        holder.bind(getItem(position))
        focusAt(position, holder)
    }

    protected open fun bindAddTextView(holder: ADDVH) {

    }
}