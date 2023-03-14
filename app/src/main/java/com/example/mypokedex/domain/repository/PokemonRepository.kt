package com.example.mypokedex.domain.repository

import com.example.mypokedex.domain.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemonList(): List<Pokemon>
    suspend fun getPokemonByType(pokemonType: String): List<Pokemon>

}