package kort.uikit.sample.checkboxlist

import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.itemEditText.CheckableEditItemModel

/**
 * Created by Kort on 2019/9/25.
 */
data class CheckBoxEditItem(
    override var id: String = "",
    override var title: String = "",
    override var order: Int = 0,
    override var isChecked: Boolean = false
) : CheckableEditItemModel