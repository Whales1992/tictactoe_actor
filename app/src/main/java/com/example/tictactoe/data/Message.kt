package com.example.tictactoe.data

import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.data.model.Move
import kotlinx.coroutines.CompletableDeferred

sealed class Message {
    class makeMove(val move: Move) : Message()
    class GetGameState(val gameState: CompletableDeferred<GameState>) : Message()
}