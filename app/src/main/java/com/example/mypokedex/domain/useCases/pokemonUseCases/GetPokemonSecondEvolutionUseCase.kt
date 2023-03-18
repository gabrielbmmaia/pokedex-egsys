package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * É checado se a Chain inserido no parâmetro contém
 * segundas evoluções do Pokemon e em caso de obter
 * a lista é enviada
 * */
class GetPokemonSecondEvolutionUseCase {
    operator fun invoke(chain: Chain): Flow<Resource<List<Pokemon>>> = flow {

        val secondEvolutionList = chain.evolvesTo
        val pokemonSecondEvolutionsList = secondEvolutionList.map { it.evolutionSecond }

        if (secondEvolutionList.isNotEmpty()) {
            emit(Resource.Success(pokemonSecondEvolutionsList))
        } else emit(Resource.Error())
    }
}
