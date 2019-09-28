package kort.uikit.sample.checkboxlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemrecyclerview.EditItemListViewModelDelegateInterface

class CheckBoxViewModel(editItemListViewModelDelegate: EditItemListViewModelDelegateInterface<CheckBoxItem>) :
    ViewModel(), EditItemListViewModelDelegateInterface<CheckBoxItem> by editItemListViewModelDelegate
