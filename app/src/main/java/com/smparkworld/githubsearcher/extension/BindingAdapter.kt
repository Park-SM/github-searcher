package com.smparkworld.githubsearcher.extension

import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smparkworld.githubsearcher.R

@BindingAdapter("onAction")
fun onAction(view: EditText, done: Function0<Unit>) {
    view.setOnEditorActionListener { _, _, _ ->
        done.invoke()
        return@setOnEditorActionListener false
    }
}

@BindingAdapter("loadCircleImg")
fun loadCircleImg(view: ImageView, url: String) {
    Glide.with(view).load(url).circleCrop().into(view)
}

@BindingAdapter("divider")
fun divider(view: RecyclerView, visible: Boolean) {
    val drawable = ContextCompat.getDrawable(view.context, R.drawable.shape_list_divier)
    if (!visible || drawable == null) return

    view.addItemDecoration(
        DividerItemDecoration(view.context, RecyclerView.VERTICAL).apply { setDrawable(drawable) }
    )
}