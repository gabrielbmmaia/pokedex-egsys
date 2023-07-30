package com.pokemon.mypokedex.data.networking

import com.pokemon.mypokedex.core.Constantes.POKEMON_FINAL_INDEX_LIST
import com.pokemon.mypokedex.data.networking.model.PokemonDetailsDto
import com.pokemon.mypokedex.data.networking.model.pokemonForms.PokemonFormsDto
import com.pokemon.mypokedex.data.networking.model.requests.EvolutionRequest
import com.pokemon.mypokedex.data.networking.model.requests.PokemonRequest
import com.pokemon.mypokedex.data.networking.model.requests.TipoRequest
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

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonDetail(
        @Path("pokemonId") pokemonId: Int
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