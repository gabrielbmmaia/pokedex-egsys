package com.example.mypokedex.core.extensions

import android.widget.ImageView
import coil.load


/**
 * Função para carregar imagem por url
 * Possível adicionar fade colocando fade = true
 * */
fun ImageView.loadImageFromUrl(pokemonId: String, fade: Boolean = false) {
    val imageUrl = getPokemonImageUrl(pokemonId)
    if (!fade)
        load(imageUrl)
    else {
        load(imageUrl) {
            crossfade(enable = true)
            crossfade(durationMillis = 500)
        }
    }
}
