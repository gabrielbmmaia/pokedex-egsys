package com.example.mypokedex.data.mappers

import com.example.mypokedex.core.extensions.formattPokemonName
import com.example.mypokedex.core.extensions.getEvolutionChainId
import com.example.mypokedex.data.model.PokemonDetailsDto
import com.example.mypokedex.data.model.PokemonDto
import com.example.mypokedex.data.model.pokemonEvolution.ChainDto
import com.example.mypokedex.data.model.pokemonForms.PokemonFormsDto
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.model.Chain
import com.example.mypokedex.domain.model.pokemonForms.PokemonForms
import com.example.mypokedex.domain.model.pokemonForms.Variety
import com.example.mypokedex.domain.model.PokemonMoves
import com.example.mypokedex.domain.model.Sprites

fun PokemonDto.toPokemon(): Pokemon {
    return Pokemon(
        name = name.formattPokemonName(),
        url = url
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
                Pokemon(
                    name = it.evolutionThird.name.formattPokemonName(),
                    url = it.evolutionThird.url
                )
            )
        }
    }

    return Chain(
        firstEvolution = Pokemon(
            name = evolutionFirst.name.formattPokemonName(),
            url = evolutionFirst.url
        ),
        secondEvolutions = evolvesTo.map {
            Pokemon(
                name = it.evolutionSecond.name.formattPokemonName(),
                url = it.evolutionSecond.url
            )
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
                pokemon = Pokemon(
                    name = it.pokemon.name.formattPokemonName(),
                    url = it.pokemon.url
                )
            )
        }
    )
}