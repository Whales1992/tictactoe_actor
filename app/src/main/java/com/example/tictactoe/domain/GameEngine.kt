//package com.example.tictactoe.domain
//
//import android.util.Log
//import com.example.tictactoe.data.model.GameState
//import com.example.tictactoe.data.model.Move
//import com.example.tictactoe.utils.IGameState
//
//class GameEngine(
//    private val player1: Player,
//    private val player2: Player,
//    private val coordinator: Coordinator,
//    private val gameState: GameState,
//    private val iGameState: IGameState
//) {
//    private var availableMoves = ArrayList<Move>()
//
//    fun start():GameEngine{
//        availableMoves.addAll(gameState.availableMoves)
//
//        coordinator.start(player1, player2, gameState)
//        var newGameState = coordinator.report()
//        newGameState.previousMove = Move(0,0)
//
//        availableMoves = newGameState.availableMoves as ArrayList<Move>
//        iGameState.onMoveMade(newGameState)
//
//        var player = player1
//
//        while(availableMoves.isNotEmpty()){
//            Log.e("@Running", "$availableMoves")
//
//            player = if(player==player1)
//                player2
//            else
//                player1
//
//            coordinator.playNext(player, newGameState, availableMoves[0])//0 can be randomize value
//            newGameState = coordinator.report()
//
//            availableMoves = newGameState.availableMoves
//            iGameState.onMoveMade(newGameState)
//        }
//
////        iGameState.onGameOver()
//
//        return this
//    }
//
//}