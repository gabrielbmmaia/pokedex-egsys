package com.example.mypokedex.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.core.extensions.loadImageFromUrl
import com.example.mypokedex.databinding.VhPokemonBinding
import com.example.mypokedex.domain.model.Pokemon

class PokemonPagingAdapter : PagingDataAdapter<Pokemon, RecyclerView.ViewHolder>(comparator) {

    companion object {
        private val comparator = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem == newItem
        }
    }

    class PokemonViewHolder(val binding: VhPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.pokemonImage.loadImageFromUrl(pokemon.id, fade = true)
            binding.pokemonName.text = pokemon.name
        }
    }

    private var onClick: ((String) -> Unit)? = null
    fun onClickItem(listener: (String) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhPokemonBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder as PokemonViewHolder
        pokemon?.let { pokemon ->
            holder.bind(pokemon)
            holder.binding.root.setOnClickListener {
                onClick?.let {
                    it(pokemon.id)
                }
            }
        }
    }
}
