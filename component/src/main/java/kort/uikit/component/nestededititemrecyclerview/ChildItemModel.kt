package kort.uikit.component.nestededititemrecyclerview

import kort.uikit.component.edititemrecyclerview.ItemModel

/**
 * Created by Kort on 2019/9/28.
 */
interface ChildItemModel : ItemModel {
    var parentId: String
    var childOrder: Int
}