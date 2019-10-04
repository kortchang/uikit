package kort.uikit.sample.list.numberlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.ListEventObserverInterface
import kort.uikit.component.edititemlist.ListEventSenderObserver
import kort.uikit.component.edititemlist.ListEventSenderObserverInterface
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegateInterface
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegate

class NumberListViewModel(
    editItemListViewModelDelegate: SingleListViewModelDelegate<NumberEditItem>
    ) :
    ViewModel(),
    SingleListViewModelDelegateInterface<NumberEditItem> by editItemListViewModelDelegate {

}