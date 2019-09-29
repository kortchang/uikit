package kort.uikit.sample.nestedlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegateInterface
import kort.uikit.sample.checkboxlist.CheckBoxItem
import kort.uikit.sample.numberlist.NumberItem

class NestedListViewModelInterface(nestedListViewModelDelegateInterface: NestedListViewModelDelegateInterface<NumberItem, CheckBoxItem>) :
    ViewModel(),
    NestedListViewModelDelegateInterface<NumberItem, CheckBoxItem> by nestedListViewModelDelegateInterface
