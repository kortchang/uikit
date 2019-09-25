package kort.uikit.sample.numberlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemrecyclerview.EditItemListViewModelDelegate

class NumberListViewModel(editItemListViewModelDelegate: EditItemListViewModelDelegate<NumberItem>) :
    ViewModel(), EditItemListViewModelDelegate<NumberItem> by editItemListViewModelDelegate
