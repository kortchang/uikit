package kort.uikit.sample.nestedlist

import kort.uikit.component.edititemlist.ItemModel

/**
 * Created by Kort on 2019/9/28.
 */
data class ParentItem(
    override var id: String = "",
    override var title: String = "",
    override var order: Int = 0
) : ItemModel