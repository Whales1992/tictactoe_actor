package com.example.tictactoe.data

import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.data.model.Move
import com.example.tictactoe.domain.Player
import kotlinx.coroutines.CompletableDeferred

sealed class Message {
    class SendMove(val player : Player, val move: Move) : Message()
    class GetGameState(val gameState: CompletableDeferred<GameState>) : Message()
}