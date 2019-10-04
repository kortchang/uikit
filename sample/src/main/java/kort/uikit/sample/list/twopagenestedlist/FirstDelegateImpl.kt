package kort.uikit.sample.list.twopagenestedlist

import androidx.lifecycle.MutableLiveData
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegate
import kort.uikit.sample.list.nestedlist.ParentEditItem

/**
 * Created by Kort on 2019/10/4.
 */
class FirstDelegateImpl : SingleListViewModelDelegate<ParentEditItem>(ParentEditItem::class) {
    override var _list: MutableLiveData<MutableList<ParentEditItem>> =
        MutableLiveData(mutableListOf(generateNewItem(0, "", generateId)))
}