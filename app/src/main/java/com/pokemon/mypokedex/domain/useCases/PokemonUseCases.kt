package com.pokemon.mypokedex.domain.useCases

import com.pokemon.mypokedex.domain.useCases.pokemonUseCases.FilterPokemonFormsUseCase
import com.pokemon.mypokedex.domain.useCases.pokemonUseCases.GetPokemonListByTypeUseCase
import com.pokemon.mypokedex.domain.useCases.pokemonUseCases.PokemonValidationUseCase
import com.pokemon.mypokedex.domain.useCases.pokemonUseCases.SearchPokemonListUseCase

/**
 * Class para englobar todos UseCases de Pokemon em apenas um lugar
 * */
data class PokemonUseCases(
    val searchPokemonList: SearchPokemonListUseCase,
    val getPokemonListByType: GetPokemonListByTypeUseCase,
    val filterPokemonForms: FilterPokemonFormsUseCase,
    val pokemonValidation: PokemonValidationUseCase
)
