package com.example.mypokedex.domain.useCases.pokemonUseCases

import android.util.Log
import com.example.mypokedex.core.Constantes.USE_CASE
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * É requisitado ao repository as evoluções do Pokemon
 * a partir do seu evolutionChanID inserido no parâmetro.
 * */
class GetPokemonEvolutionUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(evolutionChainId: Int): Flow<Resource<Chain>> = flow {

        try {
            emit(Resource.Loading)
            val result = repository.getPokemonEvolution(evolutionChainId)
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            Log.e(USE_CASE, e.stackTraceToString())
            emit(Resource.Error())
        }
    }
}
