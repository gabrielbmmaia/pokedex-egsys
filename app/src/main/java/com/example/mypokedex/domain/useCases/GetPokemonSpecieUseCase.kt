package com.example.mypokedex.domain.useCases

import com.example.mypokedex.core.Constantes.POKEMON_NAO_ENCONTRADO
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.pokemonForms.PokemonSpecie
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonSpecieUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(pokemonId: Int): Flow<Resource<PokemonSpecie>> = flow {
        try {
            val pokemonSpecie = repository.getPokemonSpecie(pokemonId)
            emit(Resource.Success(data = pokemonSpecie))
        } catch (e: Exception) {
            emit(Resource.Error(POKEMON_NAO_ENCONTRADO))
        }
    }
}