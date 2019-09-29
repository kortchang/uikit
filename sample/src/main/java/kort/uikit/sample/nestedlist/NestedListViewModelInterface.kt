package kort.uikit.sample.nestedlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegateInterface
import kort.uikit.sample.checkboxlist.CheckBoxEditItem
import kort.uikit.sample.numberlist.NumberEditItem

class NestedListViewModelInterface(nestedListViewModelDelegateInterface: NestedListViewModelDelegateInterface<NumberEditItem, CheckBoxEditItem>) :
    ViewModel(),
    NestedListViewModelDelegateInterface<NumberEditItem, CheckBoxEditItem> by nestedListViewModelDelegateInterface
