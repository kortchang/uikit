package kort.uikit.component.edititemlist

import android.service.autofill.OnClickAction
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Kort on 2019/10/14.
 */
open class ItemAddViewHolder(
    private val view: View,
    private val onClickAction: (view: View) -> Unit
) : RecyclerView.ViewHolder(view) {
    open fun bind() {
        view.setOnClickListener { onClickAction(it) }
    }
}