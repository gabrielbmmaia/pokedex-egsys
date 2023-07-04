package com.pokemon.mypokedex.domain.useCases.pokemonUseCases

import android.content.SharedPreferences
import android.util.Log
import com.pokemon.mypokedex.core.Constantes
import com.pokemon.mypokedex.domain.repository.PokemonRepository

/**
 * Adiciona lista ao banco de dados Local e
 * salva o id do ultimo pokémon ao sharedPreference
 * */
class SynchronizePokemonListUseCase(
    private val repository: PokemonRepository,
    private val preferences: SharedPreferences
) {
    suspend operator fun invoke() {
        val filteredPokemonList = repository.getFilteredPokemonList()
        if (filteredPokemonList.isNotEmpty()) {
            repository.addPokemonList(filteredPokemonList)
            val lastPokemonId = filteredPokemonList.last().id
            preferences.edit()
                .putInt(Constantes.KEY_LAST_POKEMON_NUMBER, lastPokemonId)
                .apply()
        }
    }
}