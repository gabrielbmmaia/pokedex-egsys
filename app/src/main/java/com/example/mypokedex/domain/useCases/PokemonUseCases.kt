package com.example.mypokedex.domain.useCases

import com.example.mypokedex.domain.useCases.pokemonUseCases.*

/**
 * Class para englobar todos UseCases de Pokemon em apenas um lugar
 * */
data class PokemonUseCases(
    val getPokemonList: GetPokemonListUseCase,
    val searchPokemonList: SearchPokemonListUseCase,
    val getPokemonListByType: GetPokemonListByTypeUseCase,
    val getPokemonDetails: GetPokemonDetailsUseCase,
    val filterPokemonForms: FilterPokemonFormsUseCase,
)
