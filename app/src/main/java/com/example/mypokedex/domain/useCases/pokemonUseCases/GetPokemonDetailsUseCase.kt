package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

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
    suspend operator fun invoke(pokemonOrId: String): Flow<Resource<PokemonDetails>> {
        val pokemon = pokemonValidation(pokemonOrId)
        return repository.getPokemonDetails(pokemon)
    }
}
