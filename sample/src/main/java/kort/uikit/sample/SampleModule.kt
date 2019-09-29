package kort.uikit.sample

import kort.uikit.component.edititemlist.single.EditItemListViewModelDelegateInterface
import kort.uikit.component.edititemlist.nested.NestedEditItemListViewModelDelegateInterface
import kort.uikit.sample.checkboxlist.CheckBoxItem
import kort.uikit.sample.checkboxlist.CheckBoxListViewModelDelegateImp
import kort.uikit.sample.checkboxlist.CheckBoxViewModel
import kort.uikit.sample.nestedlist.ChildItem
import kort.uikit.sample.nestedlist.NestedEditItemListViewModelDelegateImp
import kort.uikit.sample.nestedlist.NestedListViewModelInterface
import kort.uikit.sample.nestedlist.ParentItem
import kort.uikit.sample.numberlist.NumberItem
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
    factory<EditItemListViewModelDelegateInterface<NumberItem>>(named(numberDelegate)) { EditItemListViewModelDelegateImp() }
    factory<EditItemListViewModelDelegateInterface<CheckBoxItem>>(named(checkBoxDelegate)) { CheckBoxListViewModelDelegateImp() }
    factory<NestedEditItemListViewModelDelegateInterface<ParentItem, ChildItem>> { NestedEditItemListViewModelDelegateImp() }

    viewModel { NumberListViewModel(get(named(numberDelegate))) }
    viewModel { CheckBoxViewModel(get(named(checkBoxDelegate))) }
    viewModel { NestedListViewModelInterface(get()) }
}