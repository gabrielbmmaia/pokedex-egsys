package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.pokemonForms.PokemonForms

/**
 *  A partir do PokemonForms inserido no parâmetro é
 *  checado se o Pokemon tem alguma forma diferente
 *  além do padrão. Em caso de obter é enviado a lista
 *  com as formas diferentes do Pokemon
 * */
class FilterPokemonFormsUseCase {
    operator fun invoke(pokemonForms: PokemonForms): List<Pokemon> {
        val forms = pokemonForms.varieties.filter { !it.isDefault }

        return if (forms.isNotEmpty()) forms.map { it.pokemon }
        else emptyList()
    }
}
