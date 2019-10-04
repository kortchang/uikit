package kort.uikit.sample.list.checkboxlist

import kort.uikit.component.edititemlist.nested.ChildEditItemModel
import kort.uikit.component.itemEditText.CheckableEditItemModel

/**
 * Created by Kort on 2019/9/25.
 */
data class CheckBoxEditItem(
    override var id: String = "",
    override var parentId: String = "",
    override var title: String = "",
    override var order: Int = 0,
    override var isChecked: Boolean = false
) : CheckableEditItemModel, ChildEditItemModel