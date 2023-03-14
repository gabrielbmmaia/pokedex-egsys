package com.example.mypokedex.domain.useCases

import android.util.Log
import com.example.mypokedex.core.Constantes.POKEMON_LIST_ERROR_MESSAGE
import com.example.mypokedex.core.Constantes.USE_CASE
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonListByTypeUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(pokemonType: String): Flow<Resource<List<Pokemon>>> = flow {
        try {
            emit(Resource.Loading)
            val pokemonList = repository.getPokemonByType(pokemonType)
            emit(Resource.Success(pokemonList))
        } catch (e: Exception) {
            Log.e(USE_CASE, e.stackTraceToString())
            emit(Resource.Error(POKEMON_LIST_ERROR_MESSAGE))
        }
    }
}