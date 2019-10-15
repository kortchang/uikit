package kort.uikit.sample.list.addtextview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kort.uikit.component.databinding.AddTextviewBinding

import kort.uikit.sample.R
import kort.uikit.sample.databinding.FragmentAddTextViewBinding

class AddTextViewFragment : Fragment() {
    lateinit var binding: FragmentAddTextViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTextViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTextView.setOnClickListener {
            Toast.makeText(context, "onClicked", Toast.LENGTH_SHORT).show()
        }
    }
}
