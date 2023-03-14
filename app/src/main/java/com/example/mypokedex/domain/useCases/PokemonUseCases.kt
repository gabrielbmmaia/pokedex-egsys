package com.example.mypokedex.domain.useCases

data class PokemonUseCases(
    val getPokemonList: GetPokemonListUseCase,
    val getPokemonListByType: GetPokemonListByTypeUseCase
)