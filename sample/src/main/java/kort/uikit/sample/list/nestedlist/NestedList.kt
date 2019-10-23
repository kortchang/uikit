package kort.uikit.sample.list.nestedlist

import androidx.lifecycle.ViewModel
import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.edittextlist.nested.NestedListDelegateInterface
import kort.uikit.sample.list.checkboxlist.CheckBoxEditItem
import kort.uikit.sample.list.numberlist.NumberEditItem

class NestedList(nestedListDelegateInterface: NestedListDelegateInterface<NumberEditItem, CheckBoxEditItem, EditItemModel>) :
    ViewModel(),
    NestedListDelegateInterface<NumberEditItem, CheckBoxEditItem, EditItemModel> by nestedListDelegateInterface
