package com.example.mypokedex.domain.useCases.pokemonUseCases

import android.util.Log
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonFirstEvolutionUseCase {
    operator fun invoke(chain: Chain): Flow<Resource<List<Pokemon>>> = flow {
        Log.e("teste", chain.toString())
        if (chain.evolvesTo.isNotEmpty()) {
            val capim = listOf(chain.evolutionFirst)
            emit(Resource.Success(capim))
        } else emit(Resource.Error())
    }
}