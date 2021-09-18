package com.smparkworld.githubsearcher.extension

import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smparkworld.githubsearcher.R
import com.smparkworld.githubsearcher.model.Repo

@BindingAdapter("onAction")
fun onAction(view: EditText, done: Function0<Unit>) {
    view.setOnEditorActionListener { _, _, _ ->
        done.invoke()
        true
    }
}

@BindingAdapter("loadCircleImg")
fun loadCircleImg(view: ImageView, url: String?) {
    if (url != null) {
        Glide.with(view).load(url).placeholder(R.drawable.ic_github_gray).circleCrop().into(view)
    }
}

@BindingAdapter("submitList")
fun submitList(view: RecyclerView, list: List<Repo>?) {
    if (list != null) {
        view.adapter?.let {
            (it as ListAdapter<Repo, *>).submitList(list)
        }
    }
}