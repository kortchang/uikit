package kort.uikit.sample.nestedlist

import kort.uikit.component.edititemlist.nested.ChildEditItemModel
import kort.uikit.component.itemEditText.CheckableEditItemModel

/**
 * Created by Kort on 2019/9/28.
 */
data class ChildEditItem(
    override var id: String,
    override var parentId: String,
    override var title: String = "",
    override var order: Int = 0,
    override var childOrder: Int = 0,
    override var checked: Boolean = false
) : ChildEditItemModel, CheckableEditItemModel