package com.example.myapplication.data

data class Pokemon(
    val name: String,
    val url: String
)

data class PokemonListResponse(
    val results: List<Pokemon>
)