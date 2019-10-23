package kort.uikit.sample

import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.edittextlist.nested.NestedListDataStatusDelegateInterface
import kort.uikit.component.edittextlist.nested.NestedListDelegate
import kort.uikit.component.edittextlist.single.SingleListDelegateInterface
import kort.uikit.component.edittextlist.nested.NestedListDelegateInterface
import kort.uikit.sample.list.checkboxlist.CheckBoxEditItem
import kort.uikit.sample.list.checkboxlist.CheckBoxListDelegateImp
import kort.uikit.sample.list.checkboxlist.CheckBox
import kort.uikit.sample.list.datastatuslist.DataStatusListDelegateImpl
import kort.uikit.sample.list.datastatuslist.DataStatusList
import kort.uikit.sample.list.nestedlist.ChildEditItem
import kort.uikit.sample.list.nestedlist.NestedListDelegateImp
import kort.uikit.sample.list.nestedlist.NestedList
import kort.uikit.sample.list.nestedlist.ParentEditItem
import kort.uikit.sample.list.numberlist.NumberEditItem
import kort.uikit.sample.list.numberlist.NumberList
import kort.uikit.sample.list.numberlist.EditItemListDelegateImp
import kort.uikit.sample.list.twopagenestedlist.FirstDelegateImpl
import kort.uikit.sample.list.twopagenestedlist.First
import kort.uikit.sample.list.twopagenestedlist.TwoPageNestedList
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Kort on 2019/9/16.
 */
val mainModule = module {
    val numberDelegate = "number"
    val checkBoxDelegate = "checkbox"
    factory<SingleListDelegateInterface<NumberEditItem>>(named(numberDelegate)) { EditItemListDelegateImp() }
    factory<SingleListDelegateInterface<CheckBoxEditItem>>(named(checkBoxDelegate)) { CheckBoxListDelegateImp() }
    factory<NestedListDelegateInterface<ParentEditItem, ChildEditItem, EditItemModel>> { NestedListDelegateImp() }
    factory<SingleListDelegateInterface<ParentEditItem>> { FirstDelegateImpl() }
    factory<NestedListDataStatusDelegateInterface<ParentEditItem, ChildEditItem, EditItemModel>> { DataStatusListDelegateImpl() }

    viewModel { NumberList(get(named(numberDelegate))) }
    viewModel { CheckBox(get(named(checkBoxDelegate))) }
    viewModel { NestedList(get()) }
    viewModel { First(get()) }
    viewModel { (singleDelegate: SingleListDelegateInterface<ParentEditItem>) ->
        TwoPageNestedList(
            NestedListDelegate(ParentEditItem::class, ChildEditItem::class), singleDelegate
        )
    }
    viewModel { DataStatusList(get()) }
}