package kort.uikit.sample.list.nestedlist

import androidx.lifecycle.MutableLiveData
import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegate

/**
 * Created by Kort on 2019/9/25.
 */
class NestedListViewModelDelegateImp :
    NestedListViewModelDelegate<ParentEditItem, ChildEditItem, EditItemModel>(
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