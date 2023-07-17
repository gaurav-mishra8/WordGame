package com.greenbot.wordgame.domain

sealed interface GameState {
    object RUNNING : GameState
    object SUCCESS : GameState
    object CREATED : GameState
    object ERROR : GameState
}