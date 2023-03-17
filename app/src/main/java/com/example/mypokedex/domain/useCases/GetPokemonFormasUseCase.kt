package com.example.mypokedex.domain.useCases

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.pokemonForms.PokemonSpecie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonFormasUseCase {
    operator fun invoke(pokemonSpecie: PokemonSpecie): Flow<Resource<List<Pokemon>>> = flow {
        val formas = pokemonSpecie.varieties.filter { !it.isDefault }

        if (formas.isNotEmpty())
            emit(Resource.Success(formas.map { it.pokemon }))
        else emit(Resource.Error())
    }
}