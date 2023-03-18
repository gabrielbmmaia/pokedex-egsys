package com.example.mypokedex.presentation.pokemonDetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mypokedex.databinding.VhViewpagerBinding

class ViewPageAdapter : RecyclerView.Adapter<ViewPageAdapter.ViewPageViewHolder>() {

    private val artWork = mutableListOf<String>()

    inner class ViewPageViewHolder(val binding: VhViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artWork: String?) {
            artWork?.let {
                binding.pokemonImage.load(artWork)
            }
        }
    }

    fun setData(artworkList: List<String?>) {
        notifyItemRangeRemoved(0, this.artWork.size)
        this.artWork.clear()
        this.artWork.addAll(artworkList.filterNotNull())
        notifyItemInserted(this.artWork.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhViewpagerBinding.inflate(inflater, parent, false)
        return ViewPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPageViewHolder, position: Int) =
        holder.bind(artWork[position])

    override fun getItemCount(): Int = artWork.size
}
