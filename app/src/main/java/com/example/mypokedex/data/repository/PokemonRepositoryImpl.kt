package com.example.mypokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mypokedex.core.PAGING_LIMIT
import com.example.mypokedex.data.networking.PokemonServices
import com.example.mypokedex.data.paging.PokemonPagingSource
import com.example.mypokedex.domain.repository.PokemonRepository
import com.example.mypokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val pokemonServices: PokemonServices
) : PokemonRepository {

    /**
     * Cria um PagingData apartir do PokemonPagingSource
     * */
    override fun getPokemonList(): Flow<PagingData<Pokemon>> {

        val pagingSourceFactory = { PokemonPagingSource(pokemonServices = pokemonServices) }
        val pager = Pager(
            config = PagingConfig(pageSize = PAGING_LIMIT.toInt()),
            pagingSourceFactory = pagingSourceFactory
        ).flow

        return pager.map { pagingData ->
            pagingData.map { it.toPokemon() }
        }
    }
}