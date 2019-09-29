package kort.uikit.sample.checkboxlist

import kort.uikit.component.edititemlist.ItemModel

/**
 * Created by Kort on 2019/9/25.
 */
interface CheckableItemModel : ItemModel {
    var checked: Boolean
}

data class CheckBoxItem(
    override var id: String = "",
    override var title: String = "",
    override var order: Int = 0,
    override var checked: Boolean = false
) : CheckableItemModel