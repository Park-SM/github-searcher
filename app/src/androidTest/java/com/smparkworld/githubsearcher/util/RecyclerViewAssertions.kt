package com.smparkworld.githubsearcher.util

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import com.google.common.truth.Truth

object RecyclerViewAssertions {

    @JvmStatic
    fun equalItemCount(count: Int) = ViewAssertion { v, e ->
        if (e != null) throw e
        (v as? RecyclerView) ?: throw ClassCastException()

        Truth.assertThat(v.adapter?.itemCount ?: 0).isEqualTo(count)
    }

    @JvmStatic
    fun notEqualItemCount(count: Int) = ViewAssertion { v, e ->
        if (e != null) throw e
        (v as? RecyclerView) ?: throw ClassCastException()

        Truth.assertThat(v.adapter?.itemCount ?: 0).isNotEqualTo(count)
    }
}