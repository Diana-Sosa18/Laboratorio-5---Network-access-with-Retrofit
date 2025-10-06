package com.example.myapplication.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites
) {
    val displayName: String
        get() = name.replaceFirstChar { it.uppercase() }
}

data class PokemonSprites(
    val frontDefault: String?,
    val backDefault: String?,
    val frontShiny: String?,
    val backShiny: String?
)