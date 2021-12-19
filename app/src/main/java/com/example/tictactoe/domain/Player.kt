package com.example.tictactoe.domain

import android.util.Log
import com.example.tictactoe.data.Mail
import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.data.model.Move
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class Player(val id: Int) {
    lateinit var gameState: GameState

    fun makeMove(gameState: GameState, move: Move) = runBlocking<Unit> {
        val channel = basicActor()
        channel.send(Mail.SendMove(gameState, move))

        //sleep for 1 second and get state
        delay(1000)

        val deferred = CompletableDeferred<GameState>()
        channel.send(Mail.GetGameState(deferred))

        while(gameState.availableMoves.isNotEmpty()){

        }

        deferred.await()

        channel.close()
    }

    fun makeMovev1(player1: Player, player2: Player, gameState: GameState) = runBlocking<Unit> {
        val channel = basicActor()
        channel.send(Mail.Send(gameState))

        //sleep for 1 second and get state
        delay(1000)

        val deferred = CompletableDeferred<GameState>()
        channel.send(Mail.GetGameState(deferred))


        deferred.await()

        channel.close()
    }

    private fun CoroutineScope.basicActor() = actor<Mail> {
        var newGameState=GameState(Move(0,0), arrayListOf())

        for (message in channel) {
            when(message) {
                is Mail.Send -> {
                    val gameState = message.gameState
                    val availableMoves = gameState.availableMoves
                    val previousMove = gameState.previousMove

                    while(availableMoves.isNotEmpty()){
                        //make a new move based on previousMove
                        val newMove = Move(0,1)

                        gameState.availableMoves.remove(newMove)
                        gameState.previousMove = newMove

                        newGameState = gameState
                    }

//                    for(move: Move in gameState.availableMoves){
//                        if(move.x != message.move.x && move.y != message.move.y){
//                            newAvailableMove.add(move)
//                        }
//                    }

//                    newGameState = GameState((Move(message.move.x, message.move.y)), newAvailableMove)
                }

                is Mail.GetGameState -> {
                    message.gameState.complete(newGameState)
//                    gameState = newGameState
                }
            }
        }
    }

    fun getState(): GameState {
        return this.gameState
    }
}