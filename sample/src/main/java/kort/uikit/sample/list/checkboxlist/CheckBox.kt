package kort.uikit.sample.list.checkboxlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edittextlist.single.SingleListDelegateInterface

class CheckBox(singleListDelegate: SingleListDelegateInterface<CheckBoxEditItem>) :
    ViewModel(), SingleListDelegateInterface<CheckBoxEditItem> by singleListDelegate
