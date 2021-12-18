package com.example.tictactoe.domain

import com.example.tictactoe.IGameBoard
import com.example.tictactoe.data.Message
import com.example.tictactoe.data.model.GameState
import com.example.tictactoe.data.model.Move
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.runBlocking

class Cordinator(private val id: Int, private val iGameBoard: IGameBoard) : Player(id) {

    override fun Actor() = runBlocking<Unit> {
        val channel = basicActor()
        channel.send(Message.makeMove(Move(1)))

        val deferred = CompletableDeferred<GameState>()

        channel.send(Message.GetGameState(deferred))

        println(deferred.await()) // prints "3"

        channel.close()
    }

    override fun CoroutineScope.basicActor() = actor<Message> {
        var gameState = GameState(listOf())

        for (message in channel) {
            when(message) {
//                is Message.Increment -> counter += message.value
//                is Message.GetValue -> message.deferred.complete(counter)
            }
        }
    }

    fun report(iGameBoard: IGameBoard) {

    }
}