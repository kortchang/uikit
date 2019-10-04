package kort.uikit.sample.list.numberlist

import androidx.lifecycle.MutableLiveData
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegate

/**
 * Created by Kort on 2019/9/16.
 */
class EditItemListViewModelDelegateImp :
    SingleListViewModelDelegate<NumberEditItem>(NumberEditItem::class) {
    override var _list: MutableLiveData<MutableList<NumberEditItem>> = MutableLiveData(
        mutableListOf(
            NumberEditItem(id = generateId, title = "2", order = 0),
            NumberEditItem(id = generateId, title = "2", order = 1),
            NumberEditItem(id = generateId, title = "2", order = 2)
        )
    )
}