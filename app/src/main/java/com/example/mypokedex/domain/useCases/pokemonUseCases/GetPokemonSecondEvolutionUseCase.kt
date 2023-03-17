package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import com.example.mypokedex.domain.model.pokemonEvolution.Species
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonSecondEvolutionUseCase {
    operator fun invoke(chain: Chain): Flow<Resource<List<Species>>> = flow {

        val secondEvolutionList = chain.evolvesTo
        val pokemonSecondEvolutionsList = secondEvolutionList.map { it.evolutionSecond }

        if (secondEvolutionList.isNotEmpty()) {
            emit(Resource.Success(pokemonSecondEvolutionsList))
        } else emit(Resource.Error())
    }
}