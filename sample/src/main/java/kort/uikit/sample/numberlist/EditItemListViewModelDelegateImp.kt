package kort.uikit.sample.numberlist

import kort.uikit.component.edititemlist.single.SingleListViewModelDelegate

/**
 * Created by Kort on 2019/9/16.
 */
class EditItemListViewModelDelegateImp : SingleListViewModelDelegate<NumberEditItem>() {
    init {
        _list.value = mutableListOf(
            NumberEditItem(id = "0", title = "2", order = 0),
            NumberEditItem(id = "1", title = "2", order = 1),
            NumberEditItem(id = "2", title = "2", order = 2)
        )
        _id = 3
    }

    override fun generateNewItem(
        position: Int,
        title: String,
        id: String
    ) = NumberEditItem(id = id, title = title, order = position)
}