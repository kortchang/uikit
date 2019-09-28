package kort.uikit.sample.numberlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.ListEventObserverInterface
import kort.uikit.component.edititemrecyclerview.EditItemListViewModelDelegateInterface
import kort.uikit.component.edititemrecyclerview.EditItemListViewModelDelegate

class NumberListViewModel(editItemListViewModelDelegate: EditItemListViewModelDelegate<NumberItem>) :
    ViewModel(),
    EditItemListViewModelDelegateInterface<NumberItem> by editItemListViewModelDelegate