package com.example.mypokedex.presentation.pokemonDetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.core.extensions.loadPokemonTypesSprite
import com.example.mypokedex.databinding.VhPokemonTypeBinding
import com.example.mypokedex.domain.model.pokemonType.PokemonTypes

class PokemonTipoAdapter : RecyclerView.Adapter<PokemonTipoAdapter.TipoViewHolder>() {

    private val tipoList = mutableListOf<PokemonTypes>()

    inner class TipoViewHolder(val binding: VhPokemonTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(pokemonTypes: PokemonTypes) {
            binding.pokemonTipo.loadPokemonTypesSprite(pokemonTypes)
        }
    }

    fun setData(pokemonTypes: List<PokemonTypes>) {
        notifyItemRangeRemoved(0, this.tipoList.size)
        this.tipoList.clear()
        this.tipoList.addAll(pokemonTypes)
        notifyItemInserted(this.tipoList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhPokemonTypeBinding.inflate(inflater, parent, false)
        return TipoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TipoViewHolder, position: Int) =
        holder.bindView(tipoList[position])

    override fun getItemCount(): Int = tipoList.size
}
