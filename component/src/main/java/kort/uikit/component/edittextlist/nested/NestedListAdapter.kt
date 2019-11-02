package kort.uikit.component.edittextlist.nested

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kort.uikit.component.edittextlist.EditItemAdapter
import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.itemEditText.ChildEditItemModel
import kotlin.reflect.KClass

/**
 * Created by Kort on 2019/9/27.
 */
/**
 * @param [P] Parent item type.
 * @param [C] Child item type.
 * @param [TWO] Type that combine parent and child type.
 * @param [TWOVH] Parent viewHolder type.
 */
abstract class NestedListAdapter<P : EditItemModel, C : ChildEditItemModel, TWO : EditItemModel, TWOVH : RecyclerView.ViewHolder>(
    protected open val parentClass: KClass<P>,
    protected open val childClass: KClass<C>,
    protected open val diffUtil: DiffUtil.ItemCallback<TWO>
) : EditItemAdapter<TWO, TWOVH>(diffUtil) {
    protected val parentViewType = 1
    protected val childViewType = 2
    override fun getItemViewType(position: Int): Int {
        val isParent = parentClass.isInstance(getItem(position))
        val isChild = childClass.isInstance(getItem(position))
        return when {
            isParent -> parentViewType
            isChild -> childViewType
            else ->
                throw IllegalArgumentException(
                    "There is not match type of list. Item Type is ${getItem(position)::class.simpleName}"
                )
        }
    }

    abstract fun createParentViewHolder(inflater: LayoutInflater, parent: ViewGroup): TWOVH
    abstract fun createChildViewHolder(inflater: LayoutInflater, parent: ViewGroup): TWOVH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TWOVH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            parentViewType -> createParentViewHolder(inflater, parent)
            childViewType -> createChildViewHolder(inflater, parent)
            else -> throw IllegalArgumentException("There is no matched type")
        }
    }
}

abstract class NestedListWithAddAdapter<P : EditItemModel, C : ChildEditItemModel, TWO : EditItemModel, TWOVH : RecyclerView.ViewHolder>(
    override val parentClass: KClass<P>,
    override val childClass: KClass<C>,
    override val diffUtil: DiffUtil.ItemCallback<TWO>
) : NestedListAdapter<P, C, TWO, TWOVH>(parentClass, childClass, diffUtil) {
    protected val addViewType = 3
    override fun getItemCount(): Int = super.getItemCount() + 1
    override fun getItemViewType(position: Int): Int {
        var isParent = false
        var isChild = false
        var isAdd = false

        if (position != itemCount - 1) {
            isParent = parentClass.isInstance(getItem(position))
            isChild = childClass.isInstance(getItem(position))
        } else {
            isAdd = position == itemCount - 1
        }

        return when {
            isParent -> parentViewType
            isChild -> childViewType
            isAdd -> addViewType
            else ->
                throw IllegalArgumentException(
                    "There is not match type of list. Item Type is ${getItem(position)::class.simpleName}"
                )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TWOVH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            parentViewType -> createParentViewHolder(inflater, parent)
            childViewType -> createChildViewHolder(inflater, parent)
            addViewType -> createAddViewHolder(inflater, parent)
            else -> throw IllegalArgumentException("There is no matched type")
        }
    }

    abstract fun createAddViewHolder(inflater: LayoutInflater, parent: ViewGroup): TWOVH
}
