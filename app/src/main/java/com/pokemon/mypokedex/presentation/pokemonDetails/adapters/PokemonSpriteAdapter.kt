package com.pokemon.mypokedex.presentation.pokemonDetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pokemon.mypokedex.core.extensions.loadSpriteFromId
import com.pokemon.mypokedex.databinding.VhPokemonSpriteBinding
import com.pokemon.mypokedex.domain.model.Pokemon
import com.pokemon.mypokedex.presentation.pokemonDetails.adapters.PokemonSpriteAdapter.SpriteViewHolder

class PokemonSpriteAdapter(
    var onItemClicked: (pokemonId: Int) -> Unit = {}
) : RecyclerView.Adapter<SpriteViewHolder>() {

    private var spriteList = mutableListOf<Pokemon>()

    inner class SpriteViewHolder(val binding: VhPokemonSpriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var pokemon: Pokemon

        init {
            itemView.setOnClickListener {
                if (::pokemon.isInitialized) {
                    onItemClicked(pokemon.id)
                }
            }
        }

        fun bindView(pokemon: Pokemon) {
            this.pokemon = pokemon
            with(binding) {
                pokemonImage.loadSpriteFromId(pokemonId = pokemon.id, fade = true)
                pokemonName.text = pokemon.name
            }
        }
    }

    fun setData(pokemonList: List<Pokemon>) {
        notifyItemRangeRemoved(0, this.spriteList.size)
        this.spriteList.clear()
        this.spriteList.addAll(pokemonList)
        notifyItemInserted(this.spriteList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhPokemonSpriteBinding.inflate(inflater, parent, false)
        return SpriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpriteViewHolder, position: Int) =
        holder.bindView(spriteList[position])

    override fun getItemCount(): Int = spriteList.size
}
