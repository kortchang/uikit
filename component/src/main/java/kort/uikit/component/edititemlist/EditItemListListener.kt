package kort.uikit.component.edititemlist

/**
 * Created by Kort on 2019/9/29.
 */
interface EditItemListListener {
    fun onDelete(position: Int)
    fun onWrapLine(
        position: Int,
        beforeWrapLineText: String,
        afterWrapLineText: String
    )

    fun onTextChange(position: Int, changedText: String, aware: Boolean = false)
    fun requestFocus(position: Int)
}