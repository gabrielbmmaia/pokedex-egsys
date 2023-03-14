package com.example.mypokedex.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.core.Constantes.POKEMON_LIMIT_LIST
import com.example.mypokedex.core.extensions.getFormatedPokemonNumber
import com.example.mypokedex.core.extensions.loadImageFromUrl
import com.example.mypokedex.databinding.VhPokemonBinding
import com.example.mypokedex.domain.model.Pokemon

class PokemonAdapter(
    var onItemClicked: (pokemon: Pokemon) -> Unit = {}
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var pokemonList = emptyList<Pokemon>()

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
                pokemonImage.loadImageFromUrl(pokemon.id, fade = true)
                pokemonName.text = pokemon.name
                pokemonNumber.text = getFormatedPokemonNumber(pokemon)
            }
        }
    }

    fun setData(newPokemonList: List<Pokemon>) {
        val listaFiltrada = newPokemonList.filter { it.id <= POKEMON_LIMIT_LIST }
        val diffResult = DiffUtil.calculateDiff(PokemonDiffCallback(pokemonList, listaFiltrada))
        pokemonList = listaFiltrada
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhPokemonBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        holder.bindView(pokemonList[position])

    override fun getItemCount(): Int = pokemonList.size

    class PokemonDiffCallback(
        private val oldPokemonList: List<Pokemon>,
        private val newPokemonList: List<Pokemon>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldPokemonList.size
        override fun getNewListSize() = newPokemonList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPokemonList[oldItemPosition].id == newPokemonList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldPokemonList[oldItemPosition] == newPokemonList[newItemPosition]
    }
}
