package com.example.myapplication.domain.model

data class Pokemon(
    val name: String,
    val url: String
) {
    val id: Int
        get() = extractPokemonId(url)

    private fun extractPokemonId(url: String): Int {
        return url.trimEnd('/').substringAfterLast('/').toInt()
    }
}