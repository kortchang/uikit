package kort.uikit.sample.list.numberlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edittextlist.single.SingleListDelegateInterface
import kort.uikit.component.edittextlist.single.SingleListDelegate

class NumberList(
    editItemListDelegate: SingleListDelegate<NumberEditItem>
    ) :
    ViewModel(),
    SingleListDelegateInterface<NumberEditItem> by editItemListDelegate {

}