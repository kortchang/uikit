package kort.uikit.component.edititemlist.nested

import android.view.LayoutInflater
import android.view.ViewGroup
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.tool.toolbox.view.recyclerview.BaseViewHolder
import kort.uikit.component.edititemlist.ItemModel
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
abstract class NestedListAdapter<P : ItemModel, C : ChildItemModel, TWO : ItemModel, TWOVH : BaseViewHolder>(
    protected val parentClass: KClass<P>,
    protected val childClass: KClass<C>
) : BaseAdapter<TWO, TWOVH>() {
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

    abstract fun buildParentViewHolder(inflater: LayoutInflater, parent: ViewGroup): TWOVH
    abstract fun buildChildViewHolder(inflater: LayoutInflater, parent: ViewGroup): TWOVH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TWOVH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            parentViewType -> buildParentViewHolder(inflater, parent)
            childViewType -> buildChildViewHolder(inflater, parent)
            else -> throw IllegalArgumentException("There is no matched type")
        }
    }
}

