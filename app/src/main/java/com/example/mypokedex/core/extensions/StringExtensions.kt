package com.example.mypokedex.core.extensions

/**
 * Utilizado para pegar o ID do pokemon a partir do final da URL disponibilizada pela API
 * */
fun String.getPokemonId(): String {
    return this.split("/").last().removePrefix(".png")
}

/**
 * Utilizado para deixar o numero com 3 casas sendo as iniciais "0"
 * */
fun String.formatToImageId() {
    this.padStart(3, '0')
}

/**
 * Utilizado para pegar a imagem do pokemon apartir de seu ID
 * */
fun getPokemonImageUrl(
    pokemonId: String
): String = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$pokemonId.png"
