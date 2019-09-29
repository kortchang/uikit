package kort.uikit.sample.checkboxlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.single.EditItemListViewModelDelegateInterface

class CheckBoxViewModel(editItemListViewModelDelegate: EditItemListViewModelDelegateInterface<CheckBoxItem>) :
    ViewModel(), EditItemListViewModelDelegateInterface<CheckBoxItem> by editItemListViewModelDelegate
