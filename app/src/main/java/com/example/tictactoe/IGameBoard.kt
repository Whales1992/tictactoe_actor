package com.example.tictactoe

import com.example.tictactoe.data.model.Move

interface IGameBoard {
    fun onMoveMade(move : Move)
    fun onGameOver(winner: Int)
}