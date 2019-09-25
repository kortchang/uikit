package kort.uikit.sample

import kort.uikit.component.edititemrecyclerview.EditItemListViewModelDelegate
import kort.uikit.sample.checkboxlist.CheckBoxItem
import kort.uikit.sample.checkboxlist.CheckBoxListViewModelDelegateImp
import kort.uikit.sample.checkboxlist.CheckBoxViewModel
import kort.uikit.sample.numberlist.NumberItem
import kort.uikit.sample.numberlist.NumberListViewModel
import kort.uikit.sample.numberlist.NumberListViewModelDelegateImp
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Kort on 2019/9/16.
 */
val mainModule = module {
    val numberDelegate = "number"
    val checkBoxDelegate = "checkbox"
    factory<EditItemListViewModelDelegate<NumberItem>>(named(numberDelegate)) { NumberListViewModelDelegateImp() }
    factory<EditItemListViewModelDelegate<CheckBoxItem>>(named(checkBoxDelegate)) { CheckBoxListViewModelDelegateImp() }

    viewModel { NumberListViewModel(get(named(numberDelegate))) }
    viewModel { CheckBoxViewModel(get(named(checkBoxDelegate))) }
}