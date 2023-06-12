package com.example.mypokedex.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypokedex.core.extensions.formatToPokemonNumber
import com.example.mypokedex.core.extensions.getPokemonId

@Entity
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val url: String
)