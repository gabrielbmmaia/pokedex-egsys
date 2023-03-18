package com.example.mypokedex.domain.useCases

import com.example.mypokedex.domain.useCases.pokemonUseCases.*

/**
 * Class para englobar todos UseCases de Pokemon em apenas um lugar
 * */
data class PokemonUseCases(
    val getPokemonList: GetPokemonListUseCase,
    val getPokemonListByType: GetPokemonListByTypeUseCase,
    val getPokemonDetails: GetPokemonDetailsUseCase,
    val getPokemonSpecie: GetPokemonSpecieUseCase,
    val getPokemonFormas: GetPokemonFormasUseCase,
    val getPokemonEvolution: GetPokemonEvolutionUseCase,
    val getPokemonFirstEvolution: GetPokemonFirstEvolutionUseCase,
    val getPokemonSecondEvolution: GetPokemonSecondEvolutionUseCase,
    val getPokemonThirdEvolution: GetPokemonThirdEvolutionUseCase,
    val filterToLearnableAttacks: FilterToLearnableAttacksUseCase
)
