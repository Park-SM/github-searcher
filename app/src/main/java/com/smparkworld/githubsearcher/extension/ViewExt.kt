package com.smparkworld.githubsearcher.extension

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showSnackbar(textId: Int) {
    Snackbar.make(
        (window.findViewById(android.R.id.content) as ViewGroup).getChildAt(0),
        textId, Snackbar.LENGTH_SHORT
    ).show()
}