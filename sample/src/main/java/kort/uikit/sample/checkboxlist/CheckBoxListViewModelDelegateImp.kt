package kort.uikit.sample.checkboxlist

import kort.uikit.component.edititemrecyclerview.DefaultNumberListViewModelDelegate

/**
 * Created by Kort on 2019/9/25.
 */
class CheckBoxListViewModelDelegateImp :
    DefaultNumberListViewModelDelegate<CheckBoxItem>() {
    init {
        _list.value = mutableListOf(CheckBoxItem(generateId, "", 0))
    }

    override fun generateNewItem(
        position: Int,
        title: String,
        id: String
    ): CheckBoxItem = CheckBoxItem(id, title, position)
}