package kort.uikit.component.edittextlist.nested

import kort.tool.toolbox.livedata.aware
import kort.uikit.component.edittextlist.CheckableEditItemDelegate
import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.itemEditText.CheckableChildEditItemModel
import kotlin.reflect.KClass

/**
 * Created by Kort on 2019/10/23.
 */

interface NestedCheckableListStatusDelegateInterface<P : EditItemModel, C : CheckableChildEditItemModel, TWO : EditItemModel> :
    NestedListStatusDelegateInterface<P, C, TWO>, CheckableEditItemDelegate

open class NestedCheckableListStatusDelegate<P : EditItemModel, C : CheckableChildEditItemModel, TWO : EditItemModel>(
    parentClass: KClass<P>,
    childClass: KClass<C>
) : NestedListStatusDelegate<P, C, TWO>(
    parentClass,
    childClass
), NestedCheckableListStatusDelegateInterface<P, C, TWO> {
    override fun onCheckedChange(position: Int, checked: Boolean) {
        _childMap.value?.isSuccess { childMap ->
            getChildItem(position) { item ->
                getChildPosition(item) { childPosition ->
                    whenCheckedChange(childMap, position, childPosition, item, checked)
                    _childMap.aware()
                    sendChangeEventAt(position)
                }
            }
        }
    }

    open fun whenCheckedChange(
        childMap: MutableMap<String, MutableList<C>>,
        position: Int,
        childPosition: Int,
        childItem: C,
        checked: Boolean
    ) {
        childMap[childItem.parentId]?.get(childPosition)?.let {
            if(it.isChecked != checked)
                childMap[childItem.parentId]?.get(childPosition)?.isChecked = checked
        }
    }
}