package com.example.tictactoe.data.model

data class GameState(var previousMove: Move, var currentPlayerId: Int, val availableMoves : ArrayList<Move>)