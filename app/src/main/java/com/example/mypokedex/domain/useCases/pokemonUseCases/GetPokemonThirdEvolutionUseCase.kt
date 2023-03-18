package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonThirdEvolutionUseCase {
    operator fun invoke(chain: Chain): Flow<Resource<List<Pokemon>>> = flow {
        val secondEvolutionList = chain.evolvesTo
        val thirdPokemonEvolutions = mutableListOf<Pokemon>()
        secondEvolutionList.forEach { secondEvolution ->
            thirdPokemonEvolutions.addAll(secondEvolution.evolvesTo.map { it.evolutionThird })
        }
        if (thirdPokemonEvolutions.isNotEmpty())
            emit(Resource.Success(thirdPokemonEvolutions.toList()))
        else emit(Resource.Error())
    }
}
