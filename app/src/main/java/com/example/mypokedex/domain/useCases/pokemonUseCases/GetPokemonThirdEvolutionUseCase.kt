package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import com.example.mypokedex.domain.model.pokemonEvolution.Species
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonThirdEvolutionUseCase {
    operator fun invoke(chain: Chain): Flow<Resource<List<Species>>> = flow {
        val secondEvolutionList = chain.evolvesTo
        val thirdPokemonEvolutions = mutableListOf<Species>()
        secondEvolutionList.forEach { secondEvolution ->
            thirdPokemonEvolutions.addAll(secondEvolution.evolvesTo.map { it.evolutionThird })
        }
        if (thirdPokemonEvolutions.isNotEmpty())
            emit(Resource.Success(thirdPokemonEvolutions.toList()))
        else emit(Resource.Error())
    }
}
