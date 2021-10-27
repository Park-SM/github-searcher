package com.smparkworld.githubsearcher.ui.detailuser

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.smparkworld.githubsearcher.R
import org.hamcrest.Matchers.not
import org.junit.Test

@Suppress("IllegalIdentifier")
class DetailUserActivityTest {

    private val TIME_OUT = 2000L

    @Test
    fun `when repositories is not empty then the empty view should be hidden`() {

        val uid = "Park-SM"
        Intent(
            ApplicationProvider.getApplicationContext(),
            DetailUserActivity::class.java
        ).apply {
            putExtra("uid", uid)
            launchActivity<DetailUserActivity>(this)
        }

        Thread.sleep(TIME_OUT)

        onView(withId(R.id.tvEmptyRepo)).check(matches(not(isDisplayed())))
    }

    @Test
    fun `when events is not empty then the empty view should be hidden`() {

        val uid = "Park-SM"
        Intent(
            ApplicationProvider.getApplicationContext(),
            DetailUserActivity::class.java
        ).apply {
            putExtra("uid", uid)
            launchActivity<DetailUserActivity>(this)
        }

        Thread.sleep(TIME_OUT)

        onView(withId(R.id.tvEmptyEvent)).check(matches(not(isDisplayed())))
    }

    @Test
    fun `when repository is empty then the empty view should appear`() {

        // whenever do this test case, need to find a github user with empty repositories and events.
        val uid = "park-jg1"
        Intent(
            ApplicationProvider.getApplicationContext(),
            DetailUserActivity::class.java
        ).apply {
            putExtra("uid", uid)
            launchActivity<DetailUserActivity>(this)
        }

        Thread.sleep(TIME_OUT)

        onView(withId(R.id.tvEmptyRepo)).check(matches(isDisplayed()))
    }

    @Test
    fun `when event is empty then the empty view should appear`() {

        // whenever do this test case, need to find a github user with empty repositories and events.
        val uid = "park-jg1"
        Intent(
            ApplicationProvider.getApplicationContext(),
            DetailUserActivity::class.java
        ).apply {
            putExtra("uid", uid)
            launchActivity<DetailUserActivity>(this)
        }

        Thread.sleep(TIME_OUT)

        onView(withId(R.id.tvEmptyEvent)).check(matches(isDisplayed()))
    }
}