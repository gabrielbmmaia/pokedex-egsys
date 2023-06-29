package com.pokemon.mypokedex.domain.useCases.pokemonUseCases

import com.pokemon.mypokedex.domain.model.Pokemon
import com.pokemon.mypokedex.domain.model.pokemonForms.PokemonForms

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
