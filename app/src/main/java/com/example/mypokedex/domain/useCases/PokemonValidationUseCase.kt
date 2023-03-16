package com.example.mypokedex.domain.useCases

class PokemonValidationUseCase {
    operator fun invoke(pokemonOrId: String): String =
        try {
            pokemonOrId.toInt().toString()
        } catch (e: Exception) {
            pokemonOrId.lowercase().trim()
        }
}