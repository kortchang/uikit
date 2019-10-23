package kort.uikit.sample.list.twopagenestedlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edittextlist.single.SingleListDelegateInterface
import kort.uikit.sample.list.nestedlist.ParentEditItem

/**
 * Created by Kort on 2019/10/4.
 */
class First(parentDelegate: SingleListDelegateInterface<ParentEditItem>) :
    ViewModel(), SingleListDelegateInterface<ParentEditItem> by parentDelegate {
}