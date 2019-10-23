package kort.uikit.sample.list.twopagenestedlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.edittextlist.nested.NestedListDelegateInterface
import kort.uikit.component.edittextlist.single.SingleListDelegate
import kort.uikit.component.edittextlist.single.SingleListDelegateInterface
import kort.uikit.sample.list.nestedlist.ChildEditItem
import kort.uikit.sample.list.nestedlist.ParentEditItem

/**
 * Created by Kort on 2019/10/3.
 */
class TwoPageNestedList(
    nestedListDelegateInterface: NestedListDelegateInterface<ParentEditItem, ChildEditItem, EditItemModel>,
    val parentDelegate: SingleListDelegateInterface<ParentEditItem> = SingleListDelegate(
        ParentEditItem::class
    )
) : ViewModel(),
    NestedListDelegateInterface<ParentEditItem, ChildEditItem, EditItemModel> by nestedListDelegateInterface {
    init {

    }
}