package com.example.tictactoe

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.data.model.Move
import com.example.tictactoe.domain.Coordinator
import com.example.tictactoe.domain.GameEngine
import com.example.tictactoe.domain.Player
import com.example.tictactoe.presentation.component.OnMoveImpl
import com.example.tictactoe.presentation.theme.TicTacToeTheme
import com.example.tictactoe.utils.IGameState
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    lateinit var context: Context

    lateinit var gameState: GameState

    val onMoveList = ArrayList<OnMoveImpl>()

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this

        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CreateBoard()
                }
            }
        }
    }

    @DelicateCoroutinesApi
    @Composable
    fun CreateBoard() {
        val matrix = Array(9){it}
        val possibleMove = ArrayList<Move>()

        LazyColumn {
            itemsIndexed(matrix) { _, row ->
                LazyRow {
                    itemsIndexed(matrix) { _, column ->
                        possibleMove.add(Move(row, column))
                        NodeButton()

                        if(possibleMove.size == (matrix.size*matrix.size)) {
                            gameState = GameState(Move(0,0), possibleMove)

                            initGame()
                        }
                    }
                }
            }
        }
    }

    @DelicateCoroutinesApi
    private fun initGame(){
        Log.e("onMoveList", "${onMoveList.size}")
        GlobalScope.launch (Dispatchers.IO) { startGame(
            object : IGameState{
                override fun onMoveMade(gameState: GameState) {
                    GlobalScope.launch(Dispatchers.Main){
                        Log.e("onMoveMade", "$gameState")
                        onMoveList[0].setMove()
                    }
                }

                override fun onGameOver(winner: Player) {
                    TODO("Not yet implemented")
                }
            }
        ) }
    }


    private fun startGame(iGameState : IGameState){
        val player1 = Player(1)
        val player2 = Player(2)
        val coordinator = Coordinator(3)

        GameEngine(player1, player2, coordinator, gameState ,iGameState).start()
    }

    @Composable
    fun NodeButton() {
        val selected = remember { mutableStateOf(false) }
        val onMoveImpl = object : OnMoveImpl{
            override fun setMove() {
                selected.value = !selected.value
            }
        }

        onMoveList.add(onMoveImpl)

        Button(colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected.value) Color.Blue else Color.Gray),
            onClick = { selected.value = !selected.value },
            modifier = Modifier
                .padding(7.dp)
                .width(30.dp)
                .height(30.dp),
            enabled = true,

            ) {}
    }
}