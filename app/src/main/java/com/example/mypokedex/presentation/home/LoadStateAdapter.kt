package com.example.mypokedex.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.databinding.InternetErrorBinding
import com.example.mypokedex.presentation.home.LoadStateAdapter.LoadStateViewHolder

class LoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    class LoadStateViewHolder(
        val binding: InternetErrorBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadstate: LoadState) {
            binding.progressBar.isVisible = loadstate is LoadState.Loading
            binding.errorMessage.isVisible = loadstate !is LoadState.Loading
            binding.retryButton.isVisible = loadstate !is LoadState.Loading
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = InternetErrorBinding.inflate(inflater, parent, false)
                return LoadStateViewHolder(binding, retry)
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }
}