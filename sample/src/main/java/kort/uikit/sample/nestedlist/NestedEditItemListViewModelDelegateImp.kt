package kort.uikit.sample.nestedlist

import kort.uikit.component.nestededititemrecyclerview.NestedEditItemListViewModelDelegate

/**
 * Created by Kort on 2019/9/25.
 */
class NestedEditItemListViewModelDelegateImp :
    NestedEditItemListViewModelDelegate<ParentItem, ChildItem>(
        ParentItem::class,
        ChildItem::class
    ) {

    override fun generateParentItem(id: String, title: String, order: Int): ParentItem =
        ParentItem(id, title, order)

    override fun generateChildItem(
        id: String,
        parentId: String,
        title: String,
        order: Int,
        childOrder: Int
    ): ChildItem =
        ChildItem(id, parentId, title, order, childOrder)

    init {
        _parentList.value =
            mutableListOf(
                ParentItem("1", "first parent", 0),
                ParentItem("2", "second parent", 1)
            )
    }
}