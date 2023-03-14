package com.example.mypokedex.core.extensions

import android.view.View
import android.widget.ImageView
import coil.load


/**
 * Função para carregar imagem por url
 * Possível adicionar fade colocando fade = true
 * */
fun ImageView.loadImageFromUrl(pokemonId: Int, fade: Boolean = false) {
    val imageUrl = getPokemonSprite(pokemonId)
    if (!fade)
        load(imageUrl)
    else {
        load(imageUrl) {
            crossfade(enable = true)
            crossfade(durationMillis = 500)
        }
    }
}

fun View.visibilityVisible() {
    this.visibility = View.VISIBLE
}
fun View.visibilityGone(){
    this.visibility = View.GONE
}
