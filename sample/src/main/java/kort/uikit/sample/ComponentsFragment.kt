package kort.uikit.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kort.tool.toolbox.view.recyclerview.BaseAdapter
import kort.uikit.sample.databinding.*

data class Component(val title: String, @IdRes val directionId: Int)
class ComponentsFragment : Fragment() {
    private lateinit var binding: FragmentComponentsBinding
    private var componentList = listOf(
        Component("Add TextView", R.id.addTextViewFragment),
        Component("Number List", R.id.numberListFragment),
        Component("CheckBox List", R.id.checkBoxFragment),
        Component("Nested List", R.id.nestedListFragment),
        Component("Two Page Nested List", R.id.twoPageNestedListFragment)
    )
    private val adapter = ComponentsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComponentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerviewMain.adapter = adapter.apply {
            currentList = componentList.toMutableList()
            clickItemAction = {
                findNavController().navigate(it.directionId)
            }
        }
    }
}

class ComponentsAdapter : BaseAdapter<Component, ComponentsAdapter.ComponentsViewHolder>() {
    var clickItemAction: ((Component) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemComponentsBinding.inflate(inflater, parent, false)
        return ComponentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComponentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ComponentsViewHolder(private val binding: ItemComponentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Component) {
            binding.button.apply {
                text = item.title
                setOnClickListener { clickItemAction?.invoke(item) }
            }
        }
    }
}
