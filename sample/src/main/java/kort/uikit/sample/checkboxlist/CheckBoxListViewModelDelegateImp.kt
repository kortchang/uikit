package kort.uikit.sample.checkboxlist

import kort.uikit.component.edititemlist.single.SingleListViewModelDelegate

/**
 * Created by Kort on 2019/9/25.
 */
class CheckBoxListViewModelDelegateImp :
    SingleListViewModelDelegate<CheckBoxItem>() {
    init {
        _list.value = mutableListOf(CheckBoxItem(generateId, "", 0))
    }

    override fun generateNewItem(
        position: Int,
        title: String,
        id: String
    ): CheckBoxItem = CheckBoxItem(id, title, position)
}