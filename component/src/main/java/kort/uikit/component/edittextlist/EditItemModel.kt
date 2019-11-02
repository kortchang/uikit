package kort.uikit.component.edittextlist

import androidx.recyclerview.widget.DiffUtil


/**
 * Created by Kort on 2019/9/13.
 */
interface EditItemModel {
    var id: String
    var title: String
    var order: Int
}
