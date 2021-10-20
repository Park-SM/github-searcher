package com.smparkworld.githubsearcher.ui.searchuser

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.extension.RecyclerViewActionsExt.scrollToEnd
import com.smparkworld.githubsearcher.util.RecyclerViewAssertions
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test

@Suppress("IllegalIdentifier")
class SearchUserActivityTest {

    private val TIME_OUT = 2000L

    @get:Rule
    var rule = activityScenarioRule<SearchUserActivity>()

    @Test
    fun `when searching by valid keyword then itemCount is greater than 0`() {
        onView(withId(R.id.editText)).perform(
            typeText("Park-"),
            pressImeActionButton(),
            closeSoftKeyboard()
        )

        Thread.sleep(TIME_OUT)

        onView(withId(R.id.rvUsers)).check(RecyclerViewAssertions.notEqualItemCount(0))
    }

    @Test
    fun `when searching by invalid keyword then itemCount is 0`() {
        onView(withId(R.id.editText)).perform(
            typeText("f139jqfijsdi1r2u9"),
            pressImeActionButton(),
            closeSoftKeyboard()
        )

        Thread.sleep(TIME_OUT)

        onView(withId(R.id.rvUsers)).check(RecyclerViewAssertions.equalItemCount(0))
    }

    @Test
    fun `when searching by part of my ID then has my ID data within up 3 page`() {
        val keyword = "Park-"
        val myId = "Park-SM"
        val page = 3

        onView(withId(R.id.editText)).perform(
            typeText(keyword),
            pressImeActionButton(),
            closeSoftKeyboard()
        )

        Thread.sleep(TIME_OUT)

        // -1 is first page count
        for (i in 0 until page - 1) {
            onView(withId(R.id.rvUsers)).perform(scrollToEnd())
            Thread.sleep(TIME_OUT)
        }

        onView(withId(R.id.rvUsers)).perform(scrollToHolder(isInTheData(myId)))
        onView(allOf(withId(R.id.uid), withText(myId))).check(matches(isDisplayed()))
    }

    private fun isInTheData(myId: String) = object: TypeSafeMatcher<UsersAdapter.UserViewHolder>() {

        override fun matchesSafely(item: UsersAdapter.UserViewHolder?): Boolean {
            val id = item?.itemView?.findViewById<TextView>(R.id.uid)?.text

            return id != null && id == myId
        }

        override fun describeTo(description: Description?) {
            description?.appendText("There were no results found for \"$myId\".")
        }
    }
}