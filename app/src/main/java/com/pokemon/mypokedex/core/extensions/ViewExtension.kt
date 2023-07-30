package com.pokemon.mypokedex.core.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import coil.load
import com.pokemon.mypokedex.R
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_ACO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_AGUA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_DRAGAO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_ELETRICO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_FADA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_FANTASMA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_FOGO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_GELO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_INSETO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_LUTADOR
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_NORMAL
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_PEDRA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_PLANTA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_PSIQUICO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_SOMBRIO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_TERRESTRE
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_VENENOSO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_VOADOR
import com.pokemon.mypokedex.domain.model.Sprites


/**
 * Função para carregar imagem a partir do Id do Pokemon.
 * Possível adicionar fade colocando fade = true
 * */
fun ImageView.loadSpriteFromId(pokemonId: Int, fade: Boolean = false) {
    val spriteUrl = getPokemonSprite(pokemonId)
    if (!fade)
        load(spriteUrl) {
            error(R.drawable.ic_image_not_load)
        }
    else {
        load(spriteUrl) {
            crossfade(enable = true)
            crossfade(durationMillis = 500)
            error(R.drawable.ic_image_not_load)
        }
    }
}

/**
 * Da display do imagem do tipo do Pokemon a partir do seu PokemonType
 * */
fun ImageView.loadPokemonTypesSprite(pokemonTypes: String?) {
    if (pokemonTypes != null) {
        when (pokemonTypes) {
            POKEMON_TIPO_ACO -> {
                load(R.drawable.tag_aco)
            }

            POKEMON_TIPO_AGUA -> {
                load(R.drawable.tag_agua)
            }

            POKEMON_TIPO_DRAGAO -> {
                load(R.drawable.tag_dragao)
            }

            POKEMON_TIPO_ELETRICO -> {
                load(R.drawable.tag_eletrico)
            }

            POKEMON_TIPO_FADA -> {
                load(R.drawable.tag_fada)
            }

            POKEMON_TIPO_FANTASMA -> {
                load(R.drawable.tag_fantasma)
            }

            POKEMON_TIPO_FOGO -> {
                load(R.drawable.tag_fogo)
            }

            POKEMON_TIPO_GELO -> {
                load(R.drawable.tag_gelo)
            }

            POKEMON_TIPO_INSETO -> {
                load(R.drawable.tag_inseto)
            }

            POKEMON_TIPO_LUTADOR -> {
                load(R.drawable.tag_lutador)
            }

            POKEMON_TIPO_NORMAL -> {
                load(R.drawable.tag_normal)
            }

            POKEMON_TIPO_PEDRA -> {
                load(R.drawable.tag_pedra)
            }

            POKEMON_TIPO_PLANTA -> {
                load(R.drawable.tag_planta)
            }

            POKEMON_TIPO_PSIQUICO -> {
                load(R.drawable.tag_psiquico)
            }

            POKEMON_TIPO_SOMBRIO -> {
                load(R.drawable.tag_sombrio)
            }

            POKEMON_TIPO_TERRESTRE -> {
                load(R.drawable.tag_terrestre)
            }

            POKEMON_TIPO_VENENOSO -> {
                load(R.drawable.tag_venenoso)
            }

            POKEMON_TIPO_VOADOR -> {
                load(R.drawable.tag_voador)
            }
        }
    }
}

/**
 * Carrega Sprite ou Gif do Pokemon apartir de PokemonDetails.
 * Primeiramente é checado se a url do Gif não é nula. Caso não seja,
 * é carregado o Gif do Pokemon, caso seja, é carregado o Sprite.
 * É possível carregar a versão shiny do pokemon colocando pokemonShiny = true
 * */
fun ImageView.loadPokemonSpriteOrGif(
    sprites: Sprites,
    context: Context,
    pokemonShiny: Boolean
) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(ImageDecoderDecoder.Factory())
        }.build()

    if (!pokemonShiny) {
        if (sprites.animatedDefault != null) {
            load(sprites.animatedDefault, imageLoader = imageLoader) {
                visibilityVisible()
            }
        } else if (sprites.spriteDefault != null) {
            load(sprites.spriteDefault) {
                visibilityVisible()
            }
        } else visibilityGone()
    } else {
        if (sprites.animatedShiny != null) {
            load(sprites.animatedShiny, imageLoader = imageLoader) {
                visibilityVisible()
            }
        } else if (sprites.spriteShiny != null) {
            load(sprites.spriteShiny) {
                visibilityVisible()
            }
        } else visibilityGone()
    }
}

/**
 * Mostra um Toast com a mensagem enviada por parâmetro
 * */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Seta visibilidade de uma View para Visible
 * */
fun View.visibilityVisible() {
    this.visibility = View.VISIBLE
}

/**
 * Seta visibilidade de uma view para Gone
 * */
fun View.visibilityGone() {
    this.visibility = View.GONE
}

/**
 * Seta visibilidade de uma View para Invisible
 * */
fun View.visibilityInvisible() {
    this.visibility = View.INVISIBLE
}