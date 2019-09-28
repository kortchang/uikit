package kort.uikit.sample.nestedlist

import android.widget.CheckBox
import kort.uikit.component.nestededititemrecyclerview.ChildItemModel
import kort.uikit.sample.checkboxlist.CheckBoxItem
import kort.uikit.sample.checkboxlist.CheckableItemModel

/**
 * Created by Kort on 2019/9/28.
 */
data class ChildItem(
    override var id: String,
    override var parentId: String,
    override var title: String = "",
    override var order: Int = 0,
    override var childOrder: Int = 0,
    override var checked: Boolean = false
) : ChildItemModel, CheckableItemModel