package com.example.mypokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypokedex.core.extensions.formattPokemonName
import com.example.mypokedex.domain.model.Pokemon

@Entity
data class PokemonDto(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int,
    val name: String,
    val url: String
) {
    fun toPokemon(): Pokemon =
        Pokemon(
            name = name.formattPokemonName(),
            url = url
        )
}
