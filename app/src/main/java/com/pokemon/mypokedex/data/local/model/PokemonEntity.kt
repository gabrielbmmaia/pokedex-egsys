package com.pokemon.mypokedex.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val number: String
)