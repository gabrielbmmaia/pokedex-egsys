package com.example.mypokedex.domain.repository

import androidx.paging.PagingData
import com.example.mypokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<PagingData<Pokemon>>

    suspend fun getPokemonByType(pokemonType: String): List<Pokemon>

}