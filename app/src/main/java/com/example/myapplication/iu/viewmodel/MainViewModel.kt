package com.example.myapplication.iu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.PokemonRepository
import com.example.myapplication.domain.model.Pokemon  // ← IMPORT CORRECTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val pokemonList: List<Pokemon>) : UiState()
    data class Error(val message: String) : UiState()
}

class MainViewModel(
    private val repository: PokemonRepository = PokemonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadPokemonList()
    }

    fun loadPokemonList() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                repository.getPokemonList().collect { result ->
                    if (result.isSuccess) {
                        _uiState.value = UiState.Success(result.getOrThrow())
                    } else {
                        _uiState.value = UiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun refreshData() {
        loadPokemonList()
    }
}