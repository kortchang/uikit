package kort.uikit.sample.numberlist

import kort.uikit.component.edititemrecyclerview.ItemModel

/**
 * Created by Kort on 2019/9/16.
 */
data class NumberItem(
    override var id: String,
    override var title: String = "",
    override var order: Int = 0
) : ItemModel