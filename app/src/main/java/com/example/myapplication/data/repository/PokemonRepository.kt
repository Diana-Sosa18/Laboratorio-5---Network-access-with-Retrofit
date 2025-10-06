package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.remote.RetrofitClient
import com.example.myapplication.domain.model.Pokemon
import com.example.myapplication.domain.model.PokemonDetail
import com.example.myapplication.domain.model.PokemonSprites
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepository {
    private val apiService: ApiService = RetrofitClient.apiService

    suspend fun getPokemonList(): Flow<Result<List<Pokemon>>> = flow {
        try {
            val response = apiService.getPokemonList()
            val pokemonList = response.results.map { pokemonData ->
                Pokemon(
                    name = pokemonData.name,
                    url = pokemonData.url
                )
            }
            emit(Result.success(pokemonList))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun getPokemonDetail(id: Int): Flow<Result<PokemonDetail>> = flow {
        try {
            val response = apiService.getPokemonDetail(id)
            val pokemonDetail = PokemonDetail(
                id = response.id,
                name = response.name,
                sprites = PokemonSprites(
                    frontDefault = response.sprites.front_default,
                    backDefault = response.sprites.back_default,
                    frontShiny = response.sprites.front_shiny,
                    backShiny = response.sprites.back_shiny
                )
            )
            emit(Result.success(pokemonDetail))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}