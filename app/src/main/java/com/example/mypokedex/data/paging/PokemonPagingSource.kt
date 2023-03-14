package com.example.mypokedex.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mypokedex.core.INITIAL_PAGE
import com.example.mypokedex.core.PAGINGSOURCE_TAG
import com.example.mypokedex.data.model.PokemonDto
import com.example.mypokedex.data.networking.PokemonServices

class PokemonPagingSource(
    private val pokemonServices: PokemonServices
) : PagingSource<String, PokemonDto>() {

    override fun getRefreshKey(state: PagingState<String, PokemonDto>): String? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PokemonDto> {
        val currentPage = params.key ?: INITIAL_PAGE
        return try {
            val response = pokemonServices.getPokemonList()
            LoadResult.Page(
                data = response.results,
                prevKey = if (currentPage == INITIAL_PAGE) null else response.previous,
                nextKey = if (response.results.isEmpty()) null else response.next
            )
        } catch (e: Exception) {
            Log.e(PAGINGSOURCE_TAG, e.message.toString())
            LoadResult.Error(e)
        }
    }
}
