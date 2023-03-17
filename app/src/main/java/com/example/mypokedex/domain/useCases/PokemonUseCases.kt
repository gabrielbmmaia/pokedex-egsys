package com.example.mypokedex.domain.useCases

data class PokemonUseCases(
    val getPokemonList: GetPokemonListUseCase,
    val getPokemonListByType: GetPokemonListByTypeUseCase,
    val getPokemonDetails: GetPokemonDetailsUseCase,
    val getPokemonSpecie: GetPokemonSpecieUseCase,
    val getPokemonFormas: GetPokemonFormasUseCase
)