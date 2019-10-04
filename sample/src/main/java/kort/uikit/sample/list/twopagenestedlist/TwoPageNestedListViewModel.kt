package kort.uikit.sample.list.twopagenestedlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.edititemlist.ListEventSenderObserver
import kort.uikit.component.edititemlist.ListEventSenderObserverInterface
import kort.uikit.component.edititemlist.nested.ChildEditItemModel
import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegate
import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegateInterface
import kort.uikit.component.edititemlist.nested.ParentNestedListViewModelDelegate
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegate
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegateInterface
import kort.uikit.sample.list.nestedlist.ChildEditItem
import kort.uikit.sample.list.nestedlist.ParentEditItem
import kort.uikit.sample.list.numberlist.NumberEditItem

/**
 * Created by Kort on 2019/10/3.
 */
class TwoPageNestedListViewModel(
    nestedListViewModelDelegateInterface: NestedListViewModelDelegateInterface<ParentEditItem, ChildEditItem, EditItemModel>,
    val parentDelegate: SingleListViewModelDelegateInterface<ParentEditItem> = SingleListViewModelDelegate(
        ParentEditItem::class
    )
) : ViewModel(),
    NestedListViewModelDelegateInterface<ParentEditItem, ChildEditItem, EditItemModel> by nestedListViewModelDelegateInterface {
    init {

    }
}