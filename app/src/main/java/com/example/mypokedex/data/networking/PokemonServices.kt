package com.example.mypokedex.data.networking

import com.example.mypokedex.core.Constantes.POKEMON_LIMIT_LIST
import com.example.mypokedex.data.model.PokemonDetailsDto
import com.example.mypokedex.data.model.requests.PokemonRequest
import com.example.mypokedex.data.model.requests.TipoRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonServices {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = POKEMON_LIMIT_LIST
    ): PokemonRequest

    @GET("type/{tipoPokemon}")
    suspend fun getPokemonFromType(
        @Path("tipoPokemon") tipoPokemon: String
    ): TipoRequest

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonDetail(
        @Path("pokemonId") pokemonId: String
    ): PokemonDetailsDto
}