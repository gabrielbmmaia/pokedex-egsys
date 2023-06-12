package com.example.mypokedex.data.mappers

import com.example.mypokedex.core.extensions.formatToPokemonNumber
import com.example.mypokedex.core.extensions.formattPokemonName
import com.example.mypokedex.core.extensions.getEvolutionChainId
import com.example.mypokedex.core.extensions.getPokemonId
import com.example.mypokedex.data.local.model.PokemonEntity
import com.example.mypokedex.data.networking.model.PokemonDetailsDto
import com.example.mypokedex.data.networking.model.PokemonDto
import com.example.mypokedex.data.networking.model.pokemonEvolution.ChainDto
import com.example.mypokedex.data.networking.model.pokemonForms.PokemonFormsDto
import com.example.mypokedex.domain.model.Chain
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.model.PokemonMoves
import com.example.mypokedex.domain.model.Sprites
import com.example.mypokedex.domain.model.pokemonForms.PokemonForms
import com.example.mypokedex.domain.model.pokemonForms.Variety

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
        url = url
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        name = name.formattPokemonName(),
        id = id,
        number = id.formatToPokemonNumber()
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
    filteredMovesList.sortedBy { it.levelLearned }

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
        moves = filteredMovesList,
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