package com.example.tictactoe.data

import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.data.model.Move
import com.example.tictactoe.domain.Player
import kotlinx.coroutines.CompletableDeferred

sealed class Mail {
    class Send(val gameState: GameState) : Mail()
    class GetGameState(val gameState: CompletableDeferred<GameState>) : Mail()
}