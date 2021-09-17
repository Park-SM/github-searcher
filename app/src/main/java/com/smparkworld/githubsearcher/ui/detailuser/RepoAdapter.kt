package com.smparkworld.githubsearcher.ui.detailuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.githubsearcher.databinding.ItemDetailuserRepoAdapterBinding
import com.smparkworld.githubsearcher.model.Repo

class RepoAdapter : ListAdapter<Repo, RepoAdapter.RepoViewHolder>(Repo.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepoViewHolder(
            ItemDetailuserRepoAdapterBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepoViewHolder(
            private val binding: ItemDetailuserRepoAdapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repo) {
            binding.repo = item
        }
    }
}