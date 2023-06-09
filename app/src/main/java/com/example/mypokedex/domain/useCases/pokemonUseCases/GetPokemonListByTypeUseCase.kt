package com.example.mypokedex.domain.useCases.pokemonUseCases

import android.util.Log
import com.example.mypokedex.core.Constantes.POKEMON_ERROR_MESSAGE
import com.example.mypokedex.core.Constantes.USE_CASE_ERROR_TAG
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * É requisitado ao repository a lista de Pokemon do
 * tipo inserido no parâmetro
 * */
class GetPokemonListByTypeUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(pokemonType: String): Flow<Resource<List<Pokemon>>> = flow {
        try {
            emit(Resource.Loading)
            val pokemonList = repository.getPokemonByType(pokemonType)
            emit(Resource.Success(pokemonList))
        } catch (e: Exception) {
            Log.e(USE_CASE_ERROR_TAG, e.stackTraceToString())
            emit(Resource.Error(POKEMON_ERROR_MESSAGE))
        }
    }
}
