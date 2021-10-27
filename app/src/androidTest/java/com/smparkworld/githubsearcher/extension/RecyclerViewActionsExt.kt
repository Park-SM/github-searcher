package com.smparkworld.githubsearcher.extension

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matchers.allOf

object RecyclerViewActionsExt {

    @JvmStatic
    fun scrollToEnd() = object: ViewAction {

        override fun getConstraints() =
            allOf(isAssignableFrom(RecyclerView::class.java), isDisplayed())

        override fun getDescription() =
            "RecyclerView failed to scroll all the way to the end."

        override fun perform(uiController: UiController?, view: View?) {
            try {
                (view as? RecyclerView) ?: throw ClassCastException()
                val adapter = view.adapter ?: throw NullPointerException()

                view.scrollToPosition(adapter.itemCount - 1)
                uiController?.loopMainThreadUntilIdle()
            } catch (e: Exception) {
                throw PerformException.Builder()
                    .withActionDescription(description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(e)
                    .build()
            }
        }
    }
}

