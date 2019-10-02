package kort.uikit.sample.nestedlist

import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegate

/**
 * Created by Kort on 2019/9/25.
 */
class NestedListViewModelDelegateImp :
    NestedListViewModelDelegate<ParentEditItem, ChildEditItem>(
        ParentEditItem::class,
        ChildEditItem::class
    ) {

    override fun generateParentItem(id: String, title: String, order: Int): ParentEditItem =
        ParentEditItem(id, title, order)

    override fun generateChildItem(
        id: String,
        parentId: String,
        title: String,
        order: Int
    ): ChildEditItem =
        ChildEditItem(id, parentId, title, order)

    init {
        _parentList.value =
            mutableListOf(
                ParentEditItem("1", "first parent", 0),
                ParentEditItem("2", "second parent", 1)
            )
    }
}