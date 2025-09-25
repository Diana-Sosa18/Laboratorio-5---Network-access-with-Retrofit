package com.example.myapplication.iu

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Pokemon
import com.example.myapplication.data.PokemonDetail
import com.example.myapplication.network.RetrofitInstance
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    private val _pokemonList = mutableStateOf<List<Pokemon>>(emptyList())
    val pokemonList = _pokemonList

    private val _selectedPokemon = mutableStateOf<PokemonDetail?>(null)
    val selectedPokemon = _selectedPokemon

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    init {
        loadPokemonList()
    }

    fun loadPokemonList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getPokemonList()
                _pokemonList.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPokemonDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getPokemonDetail(id)
                _selectedPokemon.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

fun extractPokemonId(url: String): Int {
    return url.trimEnd('/').substringAfterLast('/').toInt()
}