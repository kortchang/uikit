package kort.uikit.sample.list.checkboxlist

import androidx.lifecycle.MutableLiveData
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegate

/**
 * Created by Kort on 2019/9/25.
 */
class CheckBoxListViewModelDelegateImp :
    SingleListViewModelDelegate<CheckBoxEditItem>(CheckBoxEditItem::class) {
    override var _list: MutableLiveData<MutableList<CheckBoxEditItem>> =
        MutableLiveData(mutableListOf(CheckBoxEditItem(generateId, title = "", order = 0)))
}