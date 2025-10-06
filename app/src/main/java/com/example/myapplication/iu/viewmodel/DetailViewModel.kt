package com.example.myapplication.iu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.PokemonRepository
import com.example.myapplication.domain.model.PokemonDetail  // ← IMPORT CORRECTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val pokemonDetail: PokemonDetail) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

class DetailViewModel(
    private val repository: PokemonRepository = PokemonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadPokemonDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                repository.getPokemonDetail(id).collect { result ->
                    if (result.isSuccess) {
                        _uiState.value = DetailUiState.Success(result.getOrThrow())
                    } else {
                        _uiState.value = DetailUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}