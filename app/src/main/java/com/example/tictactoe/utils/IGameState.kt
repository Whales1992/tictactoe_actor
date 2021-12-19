package com.example.tictactoe.utils

import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.domain.Player

interface IGameState {
    fun onMoveMade(gameState: GameState)
}