package com.example.kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin.ui.theme.KotlinTheme
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}

// NAV
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val games = remember { mutableStateListOf<Game>() }

    NavHost(
        navController = navController,
        startDestination = "principal" // Tela Inicial
    ){
        // Tela Principal
        composable("principal") {
            TelaPrincipal(games,
                onGameClick = { gameId -> navController.navigate("detalhes/$gameId")
            })
        }

        // Detalhes
        composable(
            route = "detalhes/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.LongType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getLong("gameId")
            val game = games.find { it.id == gameId }
            if (game != null) {
                Detalhes(
                    game,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

// MAIN
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TelaPrincipal(jogos: MutableList<Game>, onGameClick: (Long) -> Unit) {
    // dados
    var id by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }
    val context = LocalContext.current

    // visual
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

            Spacer(Modifier.height(10.dp))
            Text(text = "GamesRegister",
                textAlign = TextAlign.Center,
                style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            ))

            // Campos para Inserir Texto
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Titulo") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(10.dp))

            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = genero,
                    onValueChange = { genero = it },
                    label = { Text("Genero do Jogo ") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)

                )

                Spacer(Modifier.width(10.dp))
                OutlinedTextField(
                    value = ano,
                    onValueChange = { ano = it },
                    label = { Text("Ano ") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }
           //

        // Insert Data
        Button(onClick = {
            if(titulo.isNotBlank() && genero.isNotBlank() && ano.isNotBlank()) {
                val existing = jogos.any { it.titulo.equals(titulo, ignoreCase = true) }

                //Titulos iguais não entram na LISTA.
                if (existing) {
                    Toast.makeText(context, "Esse jogo já está registrado no Sistema!", Toast.LENGTH_SHORT).show()
                } else {
                    jogos.add( // add jogos na LISTA.
                        Game(
                            id = System.currentTimeMillis(),
                            titulo = titulo,
                            genero = genero,
                            ano = ano.toIntOrNull() ?: 0
                        )
                    )
                    titulo = ""; genero = ""; ano = "";
                }
            }
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar", fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // cont dos jogos
        Text(text = "Jogos cadastrados: ${jogos.size}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Coluna para a LISTA
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp))
        {
            items(jogos) { jogo ->
                ListItem(
                    headlineContent = { Text(jogo.titulo)},
                    modifier = Modifier.fillMaxWidth().combinedClickable(
                        onClick = { onGameClick(jogo.id) },
                        onLongClick = {
                            jogos.remove(jogo)
                            Toast.makeText(context, "Esse jogo foi deletado!", Toast.LENGTH_SHORT).show()
                            // TOCAR E SEGURAR EM CIMA DO NOME APAGA O JOGO.
                        }
                    )

                )
                Divider()
            }
        }
    }
}

@Composable
fun Detalhes(jogo: Game, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Título: ${jogo.titulo}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Gênero: ${jogo.genero}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Data de Lançamento: ${jogo.ano}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Voltar")
        }
    }
}