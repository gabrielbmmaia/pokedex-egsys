package com.example.mypokedex.domain.useCases

import android.util.Log
import com.example.mypokedex.core.Constantes.POKEMON_ERROR_MESSAGE
import com.example.mypokedex.core.Constantes.USE_CASE
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonDetailsUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(pokemonId: Int): Flow<Resource<PokemonDetails>> = flow {
        try {
            emit(Resource.Loading)
            val result = repository.getPokemonDetails(pokemonId)
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            Log.e(USE_CASE, e.stackTraceToString())
            emit(Resource.Error(message = POKEMON_ERROR_MESSAGE))
        }
    }
}
