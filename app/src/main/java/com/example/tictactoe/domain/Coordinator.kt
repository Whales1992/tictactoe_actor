package com.example.tictactoe.domain

import com.example.tictactoe.data.Message
import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.data.model.Move
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.runBlocking

class Coordinator(private val id: Int){
    lateinit var gameState: GameState

    private fun actor(player1: Player, player2: Player, move: Move) = runBlocking<Unit> {
        val channel = basicActor()
        channel.send(Message.SendMove(player, move))

//        val deferred = CompletableDeferred<GameState>()
//        channel.send(Message.GetGameState(deferred))

        channel.close()
    }

    private fun CoroutineScope.basicActor() = actor<Message> {

        for (message in channel) {
            when(message) {
                is Message.SendMove ->{
                    message.player.makeMove(gameState, message.move)

                    gameState = message.player.getState()
                }

                is Message.GetGameState ->{
                    message.gameState.complete(gameState)
                }
            }
        }
    }

    fun report() : GameState {
        return gameState
    }

    fun start(player1: Player, player2: Player, gameState: GameState) {
        this.gameState = gameState
        actor(player1, player2, Move(0, 0))
    }

    fun playNext(player: Player, gameState: GameState, nextPossibleMove : Move) {
        this.gameState = gameState
        actor(player, nextPossibleMove)
    }
}