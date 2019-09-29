package kort.uikit.sample

import kort.uikit.component.edititemlist.single.SingleListViewModelDelegateInterface
import kort.uikit.component.edititemlist.nested.NestedListViewModelDelegateInterface
import kort.uikit.sample.checkboxlist.CheckBoxEditItem
import kort.uikit.sample.checkboxlist.CheckBoxListViewModelDelegateImp
import kort.uikit.sample.checkboxlist.CheckBoxViewModel
import kort.uikit.sample.nestedlist.ChildEditItem
import kort.uikit.sample.nestedlist.NestedListViewModelDelegateImp
import kort.uikit.sample.nestedlist.NestedListViewModelInterface
import kort.uikit.sample.nestedlist.ParentEditItem
import kort.uikit.sample.numberlist.NumberEditItem
import kort.uikit.sample.numberlist.NumberListViewModel
import kort.uikit.sample.numberlist.EditItemListViewModelDelegateImp
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
    factory<NestedListViewModelDelegateInterface<ParentEditItem, ChildEditItem>> { NestedListViewModelDelegateImp() }

    viewModel { NumberListViewModel(get(named(numberDelegate))) }
    viewModel { CheckBoxViewModel(get(named(checkBoxDelegate))) }
    viewModel { NestedListViewModelInterface(get()) }
}