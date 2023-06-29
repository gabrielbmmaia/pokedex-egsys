package com.pokemon.mypokedex.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pokemon.mypokedex.core.extensions.getFormatedPokemonNumber
import com.pokemon.mypokedex.core.extensions.loadSpriteFromId
import com.pokemon.mypokedex.databinding.VhPokemonBinding
import com.pokemon.mypokedex.domain.model.Pokemon

class PokemonAdapter(
    private var pokemonList: List<Pokemon>,
    var onItemClicked: (pokemon: Pokemon) -> Unit = {}
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(val binding: VhPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var pokemon: Pokemon

        init {
            itemView.setOnClickListener {
                if (::pokemon.isInitialized) {
                    onItemClicked(pokemon)
                }
            }
        }

        fun bindView(pokemon: Pokemon) {
            this.pokemon = pokemon
            with(binding) {
                pokemonImage.loadSpriteFromId(pokemon.id, fade = true)
                pokemonName.text = pokemon.name
                pokemonNumber.text = getFormatedPokemonNumber(pokemon.number)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhPokemonBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        holder.bindView(pokemonList[position])

    override fun getItemCount(): Int = pokemonList.size

}
