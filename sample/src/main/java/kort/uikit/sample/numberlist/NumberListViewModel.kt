package kort.uikit.sample.numberlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.single.EditItemListViewModelDelegateInterface
import kort.uikit.component.edititemlist.single.EditItemListViewModelDelegate

class NumberListViewModel(editItemListViewModelDelegate: EditItemListViewModelDelegate<NumberItem>) :
    ViewModel(),
    EditItemListViewModelDelegateInterface<NumberItem> by editItemListViewModelDelegate