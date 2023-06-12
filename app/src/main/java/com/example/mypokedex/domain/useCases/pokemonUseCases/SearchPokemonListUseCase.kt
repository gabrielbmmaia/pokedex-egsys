package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchPokemonListUseCase(
    private val repository: PokemonRepository,
    private val validation: PokemonValidationUseCase
){
    suspend operator fun invoke(query: String): Flow<List<Pokemon>> {
        return if (query.isEmpty()) {
            repository.getPokemonList()
        }
        else {
            repository.searchPokemonList(validation(query))
        }
    }
}
