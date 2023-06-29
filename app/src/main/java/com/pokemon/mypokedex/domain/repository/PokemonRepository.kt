package com.pokemon.mypokedex.domain.repository

import com.pokemon.mypokedex.core.Resource
import com.pokemon.mypokedex.domain.model.Chain
import com.pokemon.mypokedex.domain.model.Pokemon
import com.pokemon.mypokedex.domain.model.PokemonDetails
import com.pokemon.mypokedex.domain.model.pokemonForms.PokemonForms
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
