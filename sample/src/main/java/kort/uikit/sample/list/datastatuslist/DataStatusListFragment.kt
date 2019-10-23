package kort.uikit.sample.list.datastatuslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kort.tool.toolbox.DataStatus
import kort.uikit.component.edittextlist.EditItemAdapter
import kort.uikit.component.edittextlist.EditItemDataStatusListFragment
import kort.uikit.component.edittextlist.EditItemModel
import kort.uikit.component.edittextlist.ListEventObserverInterface
import kort.uikit.sample.databinding.DataStatusListFragmentBinding
import kort.uikit.sample.list.nestedlist.NestedAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class DataStatusListFragment : EditItemDataStatusListFragment<EditItemModel>() {
    override val adapter: EditItemAdapter<EditItemModel, out RecyclerView.ViewHolder>
            by lazy { NestedAdapter(viewModel) }
    override val listObserver: ListEventObserverInterface
        get() = viewModel
    override val recyclerView: RecyclerView
        get() = binding.recyclerviewDataStatusList
    override val listLiveData: LiveData<DataStatus<MutableList<EditItemModel>>>
        get() = viewModel.list
    override val loadingDialog: AlertDialog by lazy { MaterialAlertDialogBuilder(requireContext()).create() }

    private val viewModel: DataStatusList by viewModel()
    private lateinit var binding: DataStatusListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataStatusListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
