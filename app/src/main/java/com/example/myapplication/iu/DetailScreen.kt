package com.example.myapplication.iu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.iu.viewmodel.DetailViewModel
import com.example.myapplication.iu.viewmodel.DetailUiState

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    pokemonId: Int
) {
    val uiState = viewModel.uiState.value

    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetail(pokemonId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is DetailUiState.Success -> {
                val pokemon = uiState.pokemonDetail

                Text(
                    text = "#${pokemon.id} ${pokemon.displayName}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        PokemonImage(
                            url = pokemon.sprites.frontDefault,
                            description = "Front Normal"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        PokemonImage(
                            url = pokemon.sprites.frontShiny,
                            description = "Front Shiny"
                        )
                    }

                    Column {
                        PokemonImage(
                            url = pokemon.sprites.backDefault,
                            description = "Back Normal"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        PokemonImage(
                            url = pokemon.sprites.backShiny,
                            description = "Back Shiny"
                        )
                    }
                }
            }
            is DetailUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error: ${uiState.message}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Button(onClick = { viewModel.loadPokemonDetail(pokemonId) }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonImage(url: String?, description: String) {
    androidx.compose.material3.Card {
        if (url != null) {
            AsyncImage(
                model = url,
                contentDescription = description,
                modifier = Modifier.size(120.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No image",
                    color = Color.Gray
                )
            }
        }
    }
}