package com.example.tictactoe.domain

import android.util.Log
import com.example.tictactoe.data.Mail
import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.data.model.Move
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor

class Player(private val id: Int) {

    private val movesMade = ArrayList<Move>()

    fun play(nextPlayer: Player, coordinator: Coordinator, gameState: GameState) = runBlocking<Unit>(CoroutineName("Actor$id")) {
        val channel = basicActor()
        channel.send(Mail.Send(gameState))

        //sleep for 1 second
        delay(1000)

        val deferred = CompletableDeferred<GameState>()
        channel.send(Mail.GetGameState(deferred))

        Log.e("@Coroutine Name", Thread.currentThread().name)

        val newGameState = deferred.await()

        coordinator.report(newGameState)

        if(!newGameState.foundAWinner)
            next(nextPlayer, coordinator, newGameState)

        channel.close()
    }

    private fun next(nextPlayer: Player, coordinator: Coordinator, newGameState: GameState){
        while(newGameState.availableMoves.isNotEmpty()){
            nextPlayer.play(this, coordinator, newGameState)
        }
    }

    private fun CoroutineScope.basicActor() = actor<Mail> {
        var newGameState=GameState(false, Move(0,0), -1, arrayListOf())

        for (message in channel) {
            when(message) {
                is Mail.Send -> {
                    val gameState = message.gameState
                    val availableMoves = gameState.availableMoves
                    val previousMove = gameState.previousMove

                    if(availableMoves.isNotEmpty()){
                        //make a new move based on previousMove
                        val newMove = predictNewPossibleMove(availableMoves, previousMove)

                        movesMade.add(newMove)

                        gameState.availableMoves.remove(newMove)
                        gameState.previousMove = newMove
                        gameState.currentPlayerId = id

                        //This is will only work for a 3 By 3 board, and give unexpected result for 9 * 9 or any other size
                        //It's totally random
                        if(isAWinner()){
                            gameState.foundAWinner = true
                            gameState.availableMoves = arrayListOf()
                        }

                        newGameState = gameState
                    }
                }

                is Mail.GetGameState -> {
                    message.gameState.complete(newGameState)
                }
            }
        }
    }

    private fun isAWinner():Boolean{
        if(movesMade.containsAll(WinningConstants.DL)) return true

        if(movesMade.containsAll(WinningConstants.DR)) return true

        if(movesMade.containsAll(WinningConstants.RL0)) return true

        if(movesMade.containsAll(WinningConstants.RL1)) return true

        if(movesMade.containsAll(WinningConstants.RL2)) return true

        if(movesMade.containsAll(WinningConstants.TB0)) return true

        if(movesMade.containsAll(WinningConstants.TB1)) return true

        if(movesMade.containsAll(WinningConstants.TB2)) return true

        return false
    }

    private fun predictNewPossibleMove(availableMoves : List<Move>, previousMove: Move): Move{
        //Can be made smart, currently just randomised
        val newRandomMove = (availableMoves.indices).random()
        return availableMoves[newRandomMove]
    }
}