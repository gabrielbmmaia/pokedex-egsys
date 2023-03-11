package com.example.mypokedex.presentation.home

import androidx.paging.PagingData
import com.example.mypokedex.domain.model.Pokemon

sealed class PokemonListState {
    class Data (val pokemons: PagingData<Pokemon>): PokemonListState()
    object Loading: PokemonListState()
}