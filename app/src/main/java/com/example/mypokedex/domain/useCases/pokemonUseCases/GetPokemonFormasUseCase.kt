package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.pokemonForms.PokemonSpecie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *  A partir do PokemonSpecie inserido no parâmetro é
 *  checado se o Pokemon tem alguma forma diferente
 *  além do padrão. Em caso de obter é enviado a lista
 *  com as formas do Pokemon
 * */
class GetPokemonFormasUseCase {
    operator fun invoke(pokemonSpecie: PokemonSpecie): Flow<Resource<List<Pokemon>>> = flow {
        val formas = pokemonSpecie.varieties.filter { !it.isDefault }

        if (formas.isNotEmpty())
            emit(Resource.Success(formas.map { it.pokemon }))
        else emit(Resource.Error())
    }
}
