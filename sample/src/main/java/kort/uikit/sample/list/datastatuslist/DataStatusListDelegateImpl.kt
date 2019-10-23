package kort.uikit.sample.list.datastatuslist

import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.edittextlist.nested.NestedListStatusDelegate
import kort.uikit.sample.list.nestedlist.ChildEditItem
import kort.uikit.sample.list.nestedlist.ParentEditItem

/**
 * Created by Kort on 2019/10/22.
 */
class DataStatusListDelegateImpl :
    NestedListStatusDelegate<ParentEditItem, ChildEditItem, EditItemModel>(
        ParentEditItem::class,
        ChildEditItem::class
    ) {
//    init {
//        _parentList = MutableLiveData(
//            DataStatus.Success(
//                mutableListOf(
//                    ParentEditItem(id = "1", title = "1,2")
//                )
//            )
//        )
//        _childMap = MutableLiveData(
//            DataStatus.Success(
//                mutableMapOf(
//                    "1" to mutableListOf(ChildEditItem(title = "first"))
//                )
//            )
//        )
//    }
}