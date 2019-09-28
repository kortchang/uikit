package kort.uikit.sample.nestedlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.nestededititemrecyclerview.NestedEditItemListViewModelDelegateInterface
import kort.uikit.sample.checkboxlist.CheckBoxItem
import kort.uikit.sample.numberlist.NumberItem

class NestedListViewModelInterface(nestedEditItemListViewModelDelegateInterface: NestedEditItemListViewModelDelegateInterface<NumberItem, CheckBoxItem>) :
    ViewModel(),
    NestedEditItemListViewModelDelegateInterface<NumberItem, CheckBoxItem> by nestedEditItemListViewModelDelegateInterface
