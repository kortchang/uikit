package kort.uikit.sample.checkboxlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemrecyclerview.EditItemListViewModelDelegate

class CheckBoxViewModel(editItemListViewModelDelegate: EditItemListViewModelDelegate<CheckBoxItem>) :
    ViewModel(), EditItemListViewModelDelegate<CheckBoxItem> by editItemListViewModelDelegate
