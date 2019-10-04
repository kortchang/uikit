package kort.uikit.sample.list.nestedlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegateInterface
import kort.uikit.sample.list.checkboxlist.CheckBoxEditItem
import kort.uikit.sample.list.numberlist.NumberEditItem

class NestedListViewModel(nestedListViewModelDelegateInterface: NestedListViewModelDelegateInterface<NumberEditItem, CheckBoxEditItem, EditItemModel>) :
    ViewModel(),
    NestedListViewModelDelegateInterface<NumberEditItem, CheckBoxEditItem, EditItemModel> by nestedListViewModelDelegateInterface
