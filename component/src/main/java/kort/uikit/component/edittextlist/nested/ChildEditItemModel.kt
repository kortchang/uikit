package kort.uikit.component.edittextlist.nested

import kort.uikit.component.edittextlist.EditItemModel

/**
 * Created by Kort on 2019/9/28.
 */
interface ChildEditItemModel : EditItemModel {
    var parentId: String
}