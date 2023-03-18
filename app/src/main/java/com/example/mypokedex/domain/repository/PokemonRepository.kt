package com.example.mypokedex.domain.repository

import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import com.example.mypokedex.domain.model.pokemonForms.PokemonSpecie

interface PokemonRepository {

    suspend fun getPokemonList(): List<Pokemon>
    suspend fun getPokemonByType(pokemonType: String): List<Pokemon>
    suspend fun getPokemonDetails(pokemonOrId: String): PokemonDetails
    suspend fun getPokemonSpecie(pokemonId: Int): PokemonSpecie
    suspend fun getPokemonEvolution(evolutionChainId: Int): Chain

}
