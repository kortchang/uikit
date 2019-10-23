package kort.uikit.sample.list.nestedlist

import kort.uikit.component.edittextlist.EditItemModel

/**
 * Created by Kort on 2019/9/28.
 */
data class ParentEditItem(
    override var id: String = "",
    override var title: String = "",
    override var order: Int = 0
) : EditItemModel