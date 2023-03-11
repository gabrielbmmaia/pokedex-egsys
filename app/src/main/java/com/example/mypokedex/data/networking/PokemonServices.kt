package com.example.mypokedex.data.networking

import com.example.mypokedex.core.PAGING_LIMIT
import com.example.mypokedex.data.model.PokemonRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonServices {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: String = PAGING_LIMIT
    ): PokemonRequest

}