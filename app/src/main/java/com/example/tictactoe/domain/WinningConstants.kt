package com.example.tictactoe.domain

import com.example.tictactoe.data.model.Move

object WinningConstants {
    //
    // {0,0} {0,1} {0,2}
    // {1,0} {1,1} {1,2}
    // {2,0} {2,1} {2,2}
    //
    val RL0 = arrayListOf<Move>(Move(0,0), Move(0,1), Move(0,2))
    val RL1 = arrayListOf<Move>(Move(1,0), Move(1,1), Move(1,2))
    val RL2 = arrayListOf<Move>(Move(2,0), Move(2,1), Move(2,2))

    val TB0 = arrayListOf<Move>(Move(0,0), Move(1,0), Move(2,0))
    val TB1 = arrayListOf<Move>(Move(0,1), Move(1,1), Move(2,1))
    val TB2 = arrayListOf<Move>(Move(0,2), Move(1,2), Move(2,2))

    val DR = arrayListOf<Move>(Move(0,0), Move(1,1), Move(2,2))
    val DL = arrayListOf<Move>(Move(0,2), Move(1,1), Move(2,0))
}