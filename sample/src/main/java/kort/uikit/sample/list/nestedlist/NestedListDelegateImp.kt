package kort.uikit.sample.list.nestedlist

import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.edittextlist.nested.NestedListDelegate

/**
 * Created by Kort on 2019/9/25.
 */
class NestedListDelegateImp :
    NestedListDelegate<ParentEditItem, ChildEditItem, EditItemModel>(
        ParentEditItem::class,
        ChildEditItem::class
    ) {

    init {
        setParentList(
            mutableListOf(
                ParentEditItem(generateParentId, "first parent", 0),
                ParentEditItem(generateParentId, "second parent", 1)
            )
        )
    }
}