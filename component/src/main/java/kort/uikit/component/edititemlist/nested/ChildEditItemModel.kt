package kort.uikit.component.edititemlist.nested

import kort.uikit.component.edititemlist.EditItemModel

/**
 * Created by Kort on 2019/9/28.
 */
interface ChildEditItemModel : EditItemModel {
    var parentId: String
}