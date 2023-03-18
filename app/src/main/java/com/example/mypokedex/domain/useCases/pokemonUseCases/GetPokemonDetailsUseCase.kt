package com.example.mypokedex.domain.useCases.pokemonUseCases

import android.util.Log
import com.example.mypokedex.core.Constantes.POKEMON_NAO_ENCONTRADO
import com.example.mypokedex.core.Constantes.USE_CASE
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Priemeiro valida o dado inserido no parâmetro e depois
 * é requisitado ao repository os detalhes do Pokemon.
 * Caso não exista é enviado uma mensagem de erro
 * POKEMON_NAO_ENCONTRADO
 * */
class GetPokemonDetailsUseCase(
    private val repository: PokemonRepository,
    private val pokemonValidation: PokemonValidationUseCase
) {
    operator fun invoke(pokemonOrId: String): Flow<Resource<PokemonDetails>> = flow {

        val pokemon = pokemonValidation(pokemonOrId)

        try {
            emit(Resource.Loading)
            val result = repository.getPokemonDetails(pokemon)
            emit(Resource.Success(data = result))
        } catch (e: Exception) {
            Log.e(USE_CASE, e.stackTraceToString())
            emit(Resource.Error(message = POKEMON_NAO_ENCONTRADO))
        }
    }
}
