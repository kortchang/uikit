package kort.uikit.component.edititemlist.nested

import androidx.lifecycle.LiveData
import kort.tool.toolbox.livedata.Event
import kort.uikit.component.edititemlist.*
import kort.uikit.component.edititemlist.single.SingleEditItemListListener
import kotlin.reflect.KClass

/**
 * Created by Kort on 2019/10/3.
 */
interface ParentNestedListViewModelDelegateInterface<P : EditItemModel, C : ChildEditItemModel, TWO : EditItemModel> :
    ParentNestedEditItemViewModelDelegate, SingleEditItemListListener<P>, ListEventSenderInterface

class ParentNestedListViewModelDelegate<P : EditItemModel, C : ChildEditItemModel, TWO : EditItemModel>(
    override val parentClass: KClass<P>,
    override val childClass: KClass<C>,
    override val parentListEventSender: ListEventSenderInterface
) : NestedListViewModelDelegate<P, C, TWO>(parentClass, childClass),
    ParentNestedListViewModelDelegateInterface<P, C, TWO> {
    override val itemClass: KClass<P> = parentClass

    override fun parentOnDelete(position: Int) =
        listOnDelete(_parentList, parentListEventSender, position)

    override fun parentOnWrapLine(
        position: Int,
        beforeWrapLineText: String,
        afterWrapLineText: String
    ) = listOnWrapLine(
        _parentList,
        parentListEventSender,
        position,
        beforeWrapLineText,
        afterWrapLineText,
        generateParentId
    )

    override fun parentOnTextChange(position: Int, changedText: String, aware: Boolean) =
        listOnTextChange(_parentList, parentListEventSender, position, changedText, aware)
}