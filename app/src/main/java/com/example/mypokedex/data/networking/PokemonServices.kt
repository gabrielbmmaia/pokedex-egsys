package com.example.mypokedex.data.networking

import com.example.mypokedex.core.Constantes.PAGING_LIMIT
import com.example.mypokedex.data.model.PokemonRequest
import com.example.mypokedex.data.model.TipoRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonServices {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: String = PAGING_LIMIT
    ): PokemonRequest

    @GET("type/{tipoPokemon}")
    suspend fun getPokemonFromType(
        @Path("tipoPokemon") tipoPokemon: String
    ): TipoRequest
}