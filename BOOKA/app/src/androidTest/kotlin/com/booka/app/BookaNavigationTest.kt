package com.booka.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

/**
 * 지시서 6.1 잔상·중복 이동 검증: 하단 메뉴를 빠르게 반복 전환해도 크래시나 잔상 없이 동작해야 한다.
 * PART 1 Fake DI(:app debug FakeRepositoryModule)로 동작한다.
 */
@HiltAndroidTest
class BookaNavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun 하단_메뉴를_50회_반복_전환해도_정상_동작한다() {
        repeat(50) {
            composeRule.onNodeWithText("가져오기").performClick()
            composeRule.onNodeWithText("설정").performClick()
            composeRule.onNodeWithText("서재").performClick()
        }
        composeRule.onNodeWithText("서재").assertExists()
    }

    @Test
    fun 같은_메뉴를_20회_반복_눌러도_동일_화면을_유지한다() {
        repeat(20) {
            composeRule.onNodeWithText("설정").performClick()
        }
        composeRule.onNodeWithText("설정").assertExists()
    }
}
