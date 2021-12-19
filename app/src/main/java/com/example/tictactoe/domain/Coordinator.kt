package com.example.tictactoe.domain

import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.utils.IGameState
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking

class Coordinator(private val iGameState: IGameState){
    fun report(newGameState: GameState) = runBlocking {
            iGameState.onMoveMade(newGameState)
    }
}