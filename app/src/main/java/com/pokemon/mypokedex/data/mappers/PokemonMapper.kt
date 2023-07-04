package com.pokemon.mypokedex.data.mappers

import com.pokemon.mypokedex.core.extensions.formatToPokemonNumber
import com.pokemon.mypokedex.core.extensions.formattPokemonName
import com.pokemon.mypokedex.core.extensions.getEvolutionChainId
import com.pokemon.mypokedex.core.extensions.getPokemonId
import com.pokemon.mypokedex.data.local.model.PokemonEntity
import com.pokemon.mypokedex.data.networking.model.PokemonDetailsDto
import com.pokemon.mypokedex.data.networking.model.PokemonDto
import com.pokemon.mypokedex.data.networking.model.pokemonEvolution.ChainDto
import com.pokemon.mypokedex.data.networking.model.pokemonForms.PokemonFormsDto
import com.pokemon.mypokedex.domain.model.Chain
import com.pokemon.mypokedex.domain.model.Pokemon
import com.pokemon.mypokedex.domain.model.PokemonDetails
import com.pokemon.mypokedex.domain.model.PokemonMoves
import com.pokemon.mypokedex.domain.model.Sprites
import com.pokemon.mypokedex.domain.model.pokemonForms.PokemonForms
import com.pokemon.mypokedex.domain.model.pokemonForms.Variety

fun PokemonDto.toPokemon(): Pokemon {
    return Pokemon(
        name = name.formattPokemonName(),
        id = url.getPokemonId(),
        number = url.getPokemonId().formatToPokemonNumber()
    )
}

fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = url.getPokemonId(),
        name = name,
        number = url.getPokemonId().formatToPokemonNumber()
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        name = name.formattPokemonName(),
        id = id,
        number = number
    )
}

fun Pokemon.toPokemonEntity(): PokemonEntity{
    return PokemonEntity(
        id = id,
        name = name,
        number = number
    )
}

fun PokemonDetailsDto.toPokemonDetails(): PokemonDetails {
    val filteredMovesList = moves.mapNotNull { moves ->
        moves.moveDetails.map { it.levelLearned ?: 0 }.lastOrNull { it > 0 }?.let { levelLearned ->
            PokemonMoves(
                name = moves.move.name.replaceFirstChar { it.uppercaseChar() },
                levelLearned
            )
        }
    }

    val orderedMoveList = filteredMovesList.sortedBy { it.levelLearned }


    return PokemonDetails(
        id = id,
        name = name.formattPokemonName(),
        height = height,
        weight = weight,
        sprites = Sprites(
            spriteDefault = sprites.frontDefault,
            spriteShiny = sprites.frontShiny,
            animatedDefault = sprites.versions.generationV.blackWhite.animated.frontDefault,
            animatedShiny = sprites.versions.generationV.blackWhite.animated.frontShiny,
            artWorkDefault = sprites.otherArt.officialArtwork.frontDefault,
            artWorkShiny = sprites.otherArt.officialArtwork.frontShiny
        ),
        moves = orderedMoveList,
        types = types.map { it.type.name }
    )
}

fun ChainDto.toChain(): Chain {
    val thirdEvolutions = mutableListOf<Pokemon>()
    evolvesTo.forEach { secondEvolutions ->
        secondEvolutions.evolvesTo.map {
            thirdEvolutions.add(
                it.evolutionThird.toPokemon()
            )
        }
    }

    return Chain(
        firstEvolution = evolutionFirst.toPokemon(),
        secondEvolutions = evolvesTo.map {
            it.evolutionSecond.toPokemon()
        },
        thirdEvolutions = thirdEvolutions
    )
}

fun PokemonFormsDto.toPokemonForms(): PokemonForms {
    return PokemonForms(
        evolutionChainId = evolutionChain.url.getEvolutionChainId(),
        varieties = varieties.map {
            Variety(
                isDefault = it.isDefault,
                pokemon = it.pokemon.toPokemon()
            )
        }
    )
}