package com.example.tictactoe.data.model

data class GameState(var previousMove: Move, val availableMoves : ArrayList<Move>)