package kort.uikit.sample.numberlist

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import kort.tool.test.extension.TestArchExtension
import kort.tool.test.testValue
import kort.uikit.sample.list.numberlist.EditItemListDelegateImp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

/**
 * Created by Kort on 2019/9/17.
 */
@ExperimentalCoroutinesApi
@Extensions(ExtendWith(TestArchExtension::class))
internal class NumberListViewModelDelegateImpTest {
    private val delegateImp = EditItemListDelegateImp()
    private val listInitSize get() = delegateImp.list.value?.size ?: 0

    @Nested
    internal inner class `delete item` {
        @Test
        internal fun `normal delete item`() {
            val size = listInitSize
            val deletePosition = 1
            delegateImp.onDelete(deletePosition)

            val value = delegateImp.list.testValue
            val focusAt = delegateImp.focusItemAt.testValue?.getContentIfNotHandled()

            value?.size shouldBe size - 1
            focusAt shouldBe deletePosition - 1
        }

        @Test
        internal fun `delete first item bu size greater than 1`() {
            val size = listInitSize
            delegateImp.onDelete(0)

            val value = delegateImp.list.testValue
            val focusAt = delegateImp.focusItemAt.testValue?.getContentIfNotHandled()

            value?.size shouldBe size - 1
            focusAt shouldBe 0
        }

        @Test
        internal fun `only one item left and delete`() {
            repeat(listInitSize - 1) {
                delegateImp.onDelete(it)
            }
            delegateImp.onDelete(0)

            val value = delegateImp.list.testValue

            value?.size shouldBe 1
        }
    }

    @Nested
    internal inner class `wrap line` {
        @Test
        internal fun `normal wrap line`() {
            val size = listInitSize
            val wrapLinePosition = 1
            delegateImp.onWrapLine(wrapLinePosition, "2", "")

            val value = delegateImp.list.testValue
            val focusAt = delegateImp.focusItemAt.testValue?.getContentIfNotHandled()

            value?.let {
                it[wrapLinePosition + 1].title shouldBe ""
                it[wrapLinePosition].title = "2"
            } shouldNotBe null
            focusAt shouldBe wrapLinePosition + 1
        }

        @Test
        internal fun `have afterWrapLineText wrap line`() {
            val size = listInitSize
            val beforeWrapLineText = "wrapline1"
            val afterWrapLineText = "wrapline2"
            val wrapLinePosition = 0
            delegateImp.onWrapLine(wrapLinePosition, beforeWrapLineText, afterWrapLineText)

            val value = delegateImp.list.testValue
            val focusAt = delegateImp.focusItemAt.testValue?.getContentIfNotHandled()

            value?.let {
                it.size shouldBe size + 1
                it[0].title shouldBe beforeWrapLineText
                it[1].title shouldBe afterWrapLineText

                it[0].id shouldNotBe it[1].id
            } shouldNotBe null

            focusAt shouldBe wrapLinePosition + 1
        }

        @Test
        internal fun `wrap line and push down item`() {
            val firstItemTitle = "1"
            val secondItemTitle = "2"
            val afterWrapLineText = "1.5"
            delegateImp.onWrapLine(0, firstItemTitle, "1.5")

            val value = delegateImp.list.testValue

            value?.let {
                it[0].title shouldBe firstItemTitle
                it[1].title shouldBe afterWrapLineText
                it[2].title shouldBe secondItemTitle
            }
        }
    }

    @Nested
    internal inner class `text change` {
        @Test
        internal fun `normal test change`() {
            val changedText = "changeText"
            delegateImp.onTextChange(0, changedText)

            val value = delegateImp.list.testValue

            value?.get(0)?.title shouldBe changedText
        }
    }
}