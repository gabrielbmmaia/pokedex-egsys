package com.example.mypokedex.domain.repository

import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.PokemonDetails

interface PokemonRepository {

    suspend fun getPokemonList(): List<Pokemon>
    suspend fun getPokemonByType(pokemonType: String): List<Pokemon>
    suspend fun getPokemonDetails(pokemonId: Int): PokemonDetails
}