package com.greenbot.wordgame.ui.view

import com.greenbot.wordgame.domain.GameState
import com.greenbot.wordgame.domain.WordGame

data class WordGameUiState(
    val isLoading: Boolean = true,
    val hasError: String? = null,
    val words: List<WordGame> = emptyList(),
    val currentGameState: CurrentBoardState = CurrentBoardState(),
    val gameState: GameState = GameState.CREATED
)

data class CurrentBoardState(
    val wordIndex: Int = 0,
    val charsSelected: List<Char> = emptyList(),
    val shownCharacters: List<Char> = emptyList()
)


sealed interface ViewActions {
    object LoadGame : ViewActions
    class SelectCharacter(val selectedChar: Char) : ViewActions
    object ResetGame : ViewActions
}
