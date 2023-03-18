package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A partir da Chain inserido no parâmetro é
 * checado se o Pokemon possui alguma segunda
 * evolução e em caso da lista não estar vazia
 * é enviado a lista de Primeiras Evoluções
 * */
class GetPokemonFirstEvolutionUseCase {
    operator fun invoke(chain: Chain): Flow<Resource<List<Pokemon>>> = flow {
        if (chain.evolvesTo.isNotEmpty()) {
            emit(Resource.Success(listOf(chain.evolutionFirst)))
        } else emit(Resource.Error())
    }
}
