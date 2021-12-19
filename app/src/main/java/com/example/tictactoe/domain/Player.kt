package com.example.tictactoe.domain

import android.util.Log
import com.example.tictactoe.data.Mail
import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.data.model.Move
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor

class Player(private val id: Int) {

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
        next(nextPlayer, coordinator, newGameState)

        channel.close()
    }

    private fun next(nextPlayer: Player, coordinator: Coordinator, newGameState: GameState){
        while(newGameState.availableMoves.isNotEmpty()){
            nextPlayer.play(this, coordinator, newGameState)
        }
    }

    private fun CoroutineScope.basicActor() = actor<Mail> {
        var newGameState=GameState(Move(0,0), -1, arrayListOf())

        for (message in channel) {
            when(message) {
                is Mail.Send -> {
                    val gameState = message.gameState
                    val availableMoves = gameState.availableMoves
                    val previousMove = gameState.previousMove

                    if(availableMoves.isNotEmpty()){
                        //make a new move based on previousMove
                        val newMove = predictNewPossibleMove(availableMoves, previousMove)

                        gameState.availableMoves.remove(newMove)
                        gameState.previousMove = newMove
                        gameState.currentPlayerId = id

                        newGameState = gameState
                    }
                }

                is Mail.GetGameState -> {
                    message.gameState.complete(newGameState)
                }
            }
        }
    }

    private fun predictNewPossibleMove(availableMoves : List<Move>, previousMove: Move): Move{
        return availableMoves[0]
    }

}