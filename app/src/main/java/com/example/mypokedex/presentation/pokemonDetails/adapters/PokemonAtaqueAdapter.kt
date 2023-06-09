package com.example.mypokedex.presentation.pokemonDetails.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.R
import com.example.mypokedex.databinding.VhPokemonAtaquesBinding
import com.example.mypokedex.domain.model.PokemonMoves

class PokemonAtaqueAdapter(val context: Context) :
    RecyclerView.Adapter<PokemonAtaqueAdapter.AtaqueViewHolder>() {
    private val ataqueList = mutableListOf<PokemonMoves>()

    fun setData(pokemonAtaques: List<PokemonMoves>) {
        notifyItemRangeRemoved(0, this.ataqueList.size)
        this.ataqueList.clear()
        this.ataqueList.addAll(pokemonAtaques)
        notifyItemInserted(this.ataqueList.size)
    }

    inner class AtaqueViewHolder(val binding: VhPokemonAtaquesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(pokemonMoves: PokemonMoves) {
            val ataqueLevel = pokemonMoves.levelLearned
            val ataqueName = pokemonMoves.name
            binding.pokemonAtaque.text =
                context.getString(R.string.pokemon_move_level, ataqueName, ataqueLevel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtaqueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VhPokemonAtaquesBinding.inflate(inflater, parent, false)
        return AtaqueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AtaqueViewHolder, position: Int) {
        holder.bindView(ataqueList[position])
    }

    override fun getItemCount(): Int = ataqueList.size
}
