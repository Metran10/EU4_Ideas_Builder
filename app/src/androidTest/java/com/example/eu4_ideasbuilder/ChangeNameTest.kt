package com.example.eu4_ideasbuilder


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ChangeNameTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun changeNameTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.MNB_BUTTON), withText("NEW BUILD"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.build_addition_spinner),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.constraintLayout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatSpinner.perform(click())



        val appCompatEditText = onView(
            allOf(
                withId(R.id.change_name_text_field),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.constraintLayout3),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("nowy"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.change_name_text_field), withText("nowy"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.constraintLayout3),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(pressImeActionButton())

        val materialTextView = onView(
            allOf(
                withId(R.id.change_name_confirm_text), withText("Confirm Change"),
                childAtPosition(
                    allOf(
                        withId(R.id.change_name_confirm_innerLayout),
                        childAtPosition(
                            withId(R.id.change_name_confirm_outerLayout),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.build_addition_name_title), withText("nowy"),
                withParent(withParent(withId(R.id.constraintLayout))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("nowy")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
