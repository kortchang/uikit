package kort.uikit.sample

import kort.uikit.component.edititemlist.EditItemModel
import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegate
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegateInterface
import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegateInterface
import kort.uikit.component.edititemlist.single.SingleListViewModelDelegate
import kort.uikit.sample.list.checkboxlist.CheckBoxEditItem
import kort.uikit.sample.list.checkboxlist.CheckBoxListViewModelDelegateImp
import kort.uikit.sample.list.checkboxlist.CheckBoxViewModel
import kort.uikit.sample.list.nestedlist.ChildEditItem
import kort.uikit.sample.list.nestedlist.NestedListViewModelDelegateImp
import kort.uikit.sample.list.nestedlist.NestedListViewModel
import kort.uikit.sample.list.nestedlist.ParentEditItem
import kort.uikit.sample.list.numberlist.NumberEditItem
import kort.uikit.sample.list.numberlist.NumberListViewModel
import kort.uikit.sample.list.numberlist.EditItemListViewModelDelegateImp
import kort.uikit.sample.list.twopagenestedlist.FirstDelegateImpl
import kort.uikit.sample.list.twopagenestedlist.FirstViewModel
import kort.uikit.sample.list.twopagenestedlist.TwoPageNestedListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Kort on 2019/9/16.
 */
val mainModule = module {
    val numberDelegate = "number"
    val checkBoxDelegate = "checkbox"
    factory<SingleListViewModelDelegateInterface<NumberEditItem>>(named(numberDelegate)) { EditItemListViewModelDelegateImp() }
    factory<SingleListViewModelDelegateInterface<CheckBoxEditItem>>(named(checkBoxDelegate)) { CheckBoxListViewModelDelegateImp() }
    factory<NestedListViewModelDelegateInterface<ParentEditItem, ChildEditItem, EditItemModel>> { NestedListViewModelDelegateImp() }
    factory<SingleListViewModelDelegateInterface<ParentEditItem>> { FirstDelegateImpl() }

    viewModel { NumberListViewModel(get(named(numberDelegate))) }
    viewModel { CheckBoxViewModel(get(named(checkBoxDelegate))) }
    viewModel { NestedListViewModel(get()) }
    viewModel { FirstViewModel(get()) }
    viewModel { (singleDelegate: SingleListViewModelDelegateInterface<ParentEditItem>) ->
        TwoPageNestedListViewModel(
            NestedListViewModelDelegate(ParentEditItem::class, ChildEditItem::class), singleDelegate
        )
    }
}