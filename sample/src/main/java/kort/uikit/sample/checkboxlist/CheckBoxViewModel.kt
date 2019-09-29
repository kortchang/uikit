package kort.uikit.sample.checkboxlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegateInterface

class CheckBoxViewModel(singleListViewModelDelegate: SingleListViewModelDelegateInterface<CheckBoxEditItem>) :
    ViewModel(), SingleListViewModelDelegateInterface<CheckBoxEditItem> by singleListViewModelDelegate
