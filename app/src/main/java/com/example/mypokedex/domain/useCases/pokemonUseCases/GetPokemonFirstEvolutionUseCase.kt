package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import com.example.mypokedex.domain.model.pokemonEvolution.Species
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonFirstEvolutionUseCase {
    operator fun invoke(chain: Chain): Flow<Resource<List<Species>>> = flow {
        if (chain.evolvesTo.isNotEmpty()) {
            emit(Resource.Success(listOf(chain.evolutionFirst)))
        } else emit(Resource.Error())
    }
}