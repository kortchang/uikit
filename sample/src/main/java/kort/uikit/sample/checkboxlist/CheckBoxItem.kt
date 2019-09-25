package kort.uikit.sample.checkboxlist

import kort.uikit.component.edititemrecyclerview.ItemModel

/**
 * Created by Kort on 2019/9/25.
 */
data class CheckBoxItem(
    override var id: String,
    override var title: String = "",
    override var order: Int = 0,
    var checked: Boolean = false
) : ItemModel