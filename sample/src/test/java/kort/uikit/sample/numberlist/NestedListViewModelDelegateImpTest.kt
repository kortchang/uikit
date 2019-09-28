package kort.uikit.sample.numberlist

import io.kotlintest.shouldBe
import kort.tool.test.extension.TestArchExtension
import kort.tool.test.extension.TestCoroutineExtension
import kort.tool.test.extension.TimberConverter
import kort.tool.test.testValue
import kort.uikit.sample.nestedlist.NestedEditItemListViewModelDelegateImp
import kort.uikit.sample.nestedlist.ParentItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

/**
 * Created by Kort on 2019/9/25.
 */
@ExperimentalCoroutinesApi
@Extensions(
    ExtendWith(TestCoroutineExtension::class),
    ExtendWith(TestArchExtension::class),
    ExtendWith(TimberConverter::class)
)
internal class NestedListViewModelDelegateImpTest {
    val delegateImp = NestedEditItemListViewModelDelegateImp()

    private fun initParentList() {
        delegateImp.setParentList(
            mutableListOf(
                ParentItem("0", "1", 0),
                ParentItem("1", "2", 1)
            )
        )
        delegateImp.list.testValue
    }

    @Test
    internal fun `combine parent and child`() {
        initParentList()

        var value = delegateImp.list.testValue

        println("first value: \n $value")
        value?.size shouldBe 4

        // set parent list again
        delegateImp.setParentList(mutableListOf(ParentItem("2", "3", 2)))

        value = delegateImp.list.testValue
        var child = delegateImp.childMap.testValue
        println("second value: \n $value")
//        println(child)

        value?.size shouldBe 4
    }

    @Test
    internal fun `wrap line`() {
        initParentList()
        delegateImp.onWrapLine(1, "1", "2")

        val value = delegateImp.list.testValue

        println("onWrapLine value: $value")
        value?.size shouldBe 4 + 1
    }

    @Test
    internal fun `delete item that rest of one`() {
        initParentList()
        delegateImp.onDelete(1)

        val value = delegateImp.list.testValue
        val childMap = delegateImp.childMap.testValue


        childMap?.values?.size shouldBe 2
    }
}