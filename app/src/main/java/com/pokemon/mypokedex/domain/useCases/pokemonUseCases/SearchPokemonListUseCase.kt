package com.pokemon.mypokedex.domain.useCases.pokemonUseCases

import com.pokemon.mypokedex.domain.model.Pokemon
import com.pokemon.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class SearchPokemonListUseCase(
    private val repository: PokemonRepository,
    private val validation: PokemonValidationUseCase
) {
    suspend operator fun invoke(query: String): Flow<List<Pokemon>> {
        return if (query.isEmpty()) {
            repository.getPokemonList()
        } else {
            repository.searchPokemonList(validation(query))
        }
    }
}
