package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import com.example.tictactoe.data.model.Move
import com.example.tictactoe.domain.Cordinator
import com.example.tictactoe.domain.GameEngine
import com.example.tictactoe.domain.Player
import com.example.tictactoe.presentation.theme.TicTacToeTheme
import com.example.tictactoe.presentation.ui.Node

class MainActivity : ComponentActivity(), IGameBoard {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                    StartGame(this)
                }
            }
        }
    }

    override fun onMoveMade(move: Move) {
        TODO("Not yet implemented")
    }

    override fun onGameOver(winner: Int) {
        TODO("Not yet implemented")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    TicTacToeTheme {
        Greeting("Android")
    }
}

fun StartGame(iGameBoard : IGameBoard){
    val gameMatrix  = createGameMatrix(9)

    gameMatrix.forEach { it.forEach(System.out::println) }

//    val player1 = Player(1)
//    val player2 = Player(2)
//    val cordinator = Cordinator(3, iGameBoard)
//
//    GameEngine(player1, player2, cordinator).start()
}

fun createGameMatrix(size: Int): ArrayList<List<Node>> {
    val matrix = ArrayList<List<Node>>(size)
    matrix.fill(listOf())
//    for (i in 0 until size){
//        matrix.add(Node(RectangleShape, i))
//    }
    return matrix
}