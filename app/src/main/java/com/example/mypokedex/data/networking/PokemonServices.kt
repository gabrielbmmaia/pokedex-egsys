package com.example.mypokedex.data.networking

import com.example.mypokedex.core.Constantes.POKEMON_FINAL_INDEX_LIST
import com.example.mypokedex.data.networking.model.PokemonDetailsDto
import com.example.mypokedex.data.networking.model.pokemonForms.PokemonFormsDto
import com.example.mypokedex.data.networking.model.requests.EvolutionRequest
import com.example.mypokedex.data.networking.model.requests.PokemonRequest
import com.example.mypokedex.data.networking.model.requests.TipoRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonServices {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = POKEMON_FINAL_INDEX_LIST
    ): PokemonRequest

    @GET("type/{tipoPokemon}")
    suspend fun getPokemonFromType(
        @Path("tipoPokemon") tipoPokemon: String
    ): TipoRequest

    @GET("pokemon/{pokemonOrId}")
    suspend fun getPokemonDetail(
        @Path("pokemonOrId") pokemonOrId: String
    ): PokemonDetailsDto

    @GET("pokemon-species/{pokemonId}")
    suspend fun getPokemonSpecie(
        @Path("pokemonId") pokemonId: Int
    ): PokemonFormsDto

    @GET("evolution-chain/{evolutionChainId}")
    suspend fun getPokemonEvolution(
        @Path("evolutionChainId") evolutionChainId: Int
    ): EvolutionRequest
}
