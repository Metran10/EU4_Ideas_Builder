package com.example.eu4_ideasbuilder


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ListingMenuTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun listingMenuTest() {
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

        val checkedTextView = onView(
            allOf(
                withId(android.R.id.text1), withText("Menu"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        checkedTextView.check(matches(isDisplayed()))

        val checkedTextView2 = onView(
            allOf(
                withId(android.R.id.text1), withText("Create New Build"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        checkedTextView2.check(matches(isDisplayed()))

        val checkedTextView3 = onView(
            allOf(
                withId(android.R.id.text1), withText("Go to Current Build"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        checkedTextView3.check(matches(isDisplayed()))

        val checkedTextView4 = onView(
            allOf(
                withId(android.R.id.text1), withText("Change Build Name"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        checkedTextView4.check(matches(isDisplayed()))

        val checkedTextView5 = onView(
            allOf(
                withId(android.R.id.text1), withText("Add Group"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        checkedTextView5.check(matches(isDisplayed()))

        val checkedTextView6 = onView(
            allOf(
                withId(android.R.id.text1), withText("Load Build"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        checkedTextView6.check(matches(isDisplayed()))

        val checkedTextView7 = onView(
            allOf(
                withId(android.R.id.text1), withText("Save Current Build"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        checkedTextView7.check(matches(isDisplayed()))
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
