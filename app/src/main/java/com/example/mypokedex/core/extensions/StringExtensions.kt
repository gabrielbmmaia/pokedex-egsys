package com.example.mypokedex.core.extensions

import com.example.mypokedex.core.BASE_POKEMON_URL
import com.example.mypokedex.domain.model.Pokemon

/**
 * Utilizado para pegar o ID do pokemon a partir do final da URL disponibilizada pela API
 * */
fun String.getPokemonId(): String {
    return this.replace(BASE_POKEMON_URL, "")
        .replace("/", "")
}

/**
 * Utilizado para deixar o numero com 3 casas sendo as iniciais "0"
 * */
fun String.formatToPokemonNumber(): String {
    return this.padStart(3, '0')
}

/**
 * Utilizado para pegar a url da imagem do pokemon apartir de seu ID
 * */
fun getPokemonSprite(
    pokemonId: String
): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

fun getFormatedPokemonNumber(pokemon: Pokemon): String =
    "No. ${pokemon.numero}"


//    "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$pokemonId.png"
//fun formatPokemonName(pokemon: Pokemon): String {
//    pokemon.name
//}
