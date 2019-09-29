package kort.uikit.component.edititemlist.nested

import kort.uikit.component.edititemlist.ItemModel

/**
 * Created by Kort on 2019/9/28.
 */
interface ChildItemModel : ItemModel {
    var parentId: String
    var childOrder: Int
}