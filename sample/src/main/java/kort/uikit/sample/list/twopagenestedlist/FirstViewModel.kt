package kort.uikit.sample.list.twopagenestedlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegateInterface
import kort.uikit.sample.list.nestedlist.ParentEditItem

/**
 * Created by Kort on 2019/10/4.
 */
class FirstViewModel(parentDelegate: SingleListViewModelDelegateInterface<ParentEditItem>) :
    ViewModel(), SingleListViewModelDelegateInterface<ParentEditItem> by parentDelegate {
}