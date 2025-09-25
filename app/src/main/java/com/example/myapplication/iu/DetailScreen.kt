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
import androidx.compose.material3.Card
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

@Composable
fun DetailScreen(
    viewModel: PokemonViewModel,
    pokemonId: Int
) {
    val pokemonDetail = viewModel.selectedPokemon.value
    val isLoading = viewModel.isLoading.value

    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetail(pokemonId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (pokemonDetail != null) {
            val pokemon = pokemonDetail

            Text(
                text = "#${pokemon.id} ${pokemon.name.replaceFirstChar { it.uppercase() }}",
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
                        url = pokemon.sprites.front_default,
                        description = "Front Normal"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PokemonImage(
                        url = pokemon.sprites.front_shiny,
                        description = "Front Shiny"
                    )
                }

                Column {
                    PokemonImage(
                        url = pokemon.sprites.back_default,
                        description = "Back Normal"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PokemonImage(
                        url = pokemon.sprites.back_shiny,
                        description = "Back Shiny"
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonImage(url: String?, description: String) {
    Card {
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