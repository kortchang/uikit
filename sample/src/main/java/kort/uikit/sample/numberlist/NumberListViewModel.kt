package kort.uikit.sample.numberlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegateInterface
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegate

class NumberListViewModel(editItemListViewModelDelegate: SingleListViewModelDelegate<NumberItem>) :
    ViewModel(),
    SingleListViewModelDelegateInterface<NumberItem> by editItemListViewModelDelegate