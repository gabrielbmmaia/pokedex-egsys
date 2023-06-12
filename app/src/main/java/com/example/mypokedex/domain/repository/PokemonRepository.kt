package com.example.mypokedex.domain.repository

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Chain
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.model.pokemonForms.PokemonForms
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonList(): Flow<List<Pokemon>>
    suspend fun getPokemonByType(pokemonType: String): List<Pokemon>
    suspend fun getPokemonDetails(pokemonId: Int): Flow<Resource<PokemonDetails>>
    suspend fun getPokemonForms(pokemonId: Int): Flow<Resource<PokemonForms>>
    suspend fun getPokemonEvolution(evolutionChainId: Int): Flow<Resource<Chain>>
    suspend fun synchronizePokemonList()
    suspend fun searchPokemonList(name: String): Flow<List<Pokemon>>
}
