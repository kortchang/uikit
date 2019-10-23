package kort.uikit.sample.list.twopagenestedlist

import androidx.lifecycle.MutableLiveData
import kort.uikit.component.edittextlist.single.SingleListDelegate
import kort.uikit.sample.list.nestedlist.ParentEditItem

/**
 * Created by Kort on 2019/10/4.
 */
class FirstDelegateImpl : SingleListDelegate<ParentEditItem>(ParentEditItem::class) {
    override var _list: MutableLiveData<MutableList<ParentEditItem>> =
        MutableLiveData(mutableListOf(generateNewItem(0, "", generateId)))
}