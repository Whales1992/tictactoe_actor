package com.example.tictactoe.data.model

data class GameState(var foundAWinner: Boolean, var previousMove: Move, var currentPlayerId: Int, var availableMoves : ArrayList<Move>)