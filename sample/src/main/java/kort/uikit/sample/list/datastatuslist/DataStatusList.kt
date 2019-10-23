package kort.uikit.sample.list.datastatuslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kort.tool.toolbox.DataStatus
import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.edittextlist.nested.NestedListDataStatusDelegateInterface
import kort.uikit.sample.list.nestedlist.ChildEditItem
import kort.uikit.sample.list.nestedlist.ParentEditItem

class DataStatusList(
    delegateImpl: NestedListDataStatusDelegateInterface<ParentEditItem, ChildEditItem, EditItemModel>
) : ViewModel(),
    NestedListDataStatusDelegateInterface<ParentEditItem, ChildEditItem, EditItemModel> by delegateImpl {
    init {
        val parentLiveData:MutableLiveData<DataStatus<MutableList<ParentEditItem>>> =
            MutableLiveData(
                DataStatus.Success(
                    mutableListOf(
                        ParentEditItem(id = "1", title = "1,2")
                    )
                )
            )
        val childLiveData:MutableLiveData<DataStatus<MutableMap<String, MutableList<ChildEditItem>>>> = MutableLiveData(
            DataStatus.Success(
                mutableMapOf(
                    "1" to mutableListOf(ChildEditItem(title = "first", parentId = "1"))
                )
            )
        )

        setParentLiveData(parentLiveData)
        setChildLiveData(childLiveData)
    }
}
