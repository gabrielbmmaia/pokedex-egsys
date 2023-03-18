package com.example.mypokedex.core.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import coil.load
import com.example.mypokedex.R
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_ACO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_AGUA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_DRAGAO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_ELETRICO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_FADA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_FANTASMA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_FOGO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_GELO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_INSETO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_LUTADOR
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_NORMAL
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_PEDRA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_PLANTA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_PSIQUICO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_SOMBRIO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_TERRESTRE
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_VENENOSO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_VOADOR
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.model.pokemonMove.PokemonMoves
import com.example.mypokedex.domain.model.pokemonType.PokemonTypes


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
fun ImageView.loadPokemonTypesSprite(pokemonTypes: PokemonTypes) {
    if (pokemonTypes.type.name != null) {
        when (pokemonTypes.type.name) {
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
    pokemon: PokemonDetails,
    context: Context,
    pokemonShiny: Boolean
) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(ImageDecoderDecoder.Factory())
        }.build()
    val pokemonSprite = pokemon.sprites
    val animatedSprite = pokemonSprite.versions.generationV.blackWhite.animated

    if (!pokemonShiny) {
        if (animatedSprite.frontDefault != null) {
            load(animatedSprite.frontDefault, imageLoader = imageLoader) {
                visibilityVisible()
            }
        } else if (pokemonSprite.frontDefault != null) {
            load(pokemonSprite.frontDefault) {
                visibilityVisible()
            }
        } else visibilityGone()
    } else {
        if (animatedSprite.frontShiny != null) {
            load(animatedSprite.frontShiny, imageLoader = imageLoader) {
                visibilityVisible()
            }
        } else if (pokemonSprite.frontShiny != null) {
            load(pokemonSprite.frontShiny) {
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
