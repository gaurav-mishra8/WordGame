package com.greenbot.wordgame.ui.view

// 9819817483
//ronak.harkhani@phonepe.com

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenbot.wordgame.domain.FetchRandomCharacters
import com.greenbot.wordgame.domain.FetchWordsUseCase
import com.greenbot.wordgame.domain.GameState
import com.greenbot.wordgame.domain.Result
import com.greenbot.wordgame.domain.WordGame
import kotlinx.coroutines.launch

class WordGameViewModel(
    private val fetchWordsUseCase: FetchWordsUseCase,
    private val fetchRandomCharacters: FetchRandomCharacters
) : ViewModel() {

    private val _uiState = MutableLiveData<WordGameUiState>()
    val uiState: LiveData<WordGameUiState> = _uiState

    fun accept(viewActions: ViewActions) {
        when (viewActions) {
            is ViewActions.LoadGame -> {
                fetchWords()
            }

            is ViewActions.SelectCharacter -> {
                handleCharSelection(viewActions.selectedChar)
            }

            is ViewActions.ResetGame -> {
                // no op
            }
        }
    }

    private fun fetchWords() {
        val result = fetchWordsUseCase.execute()
        bindUI(result)
    }

    private fun bindUI(result: Result<List<WordGame>>) {
        when (result) {
            is Result.Success -> {
                _uiState.value =
                    getUiState().copy(isLoading = false, hasError = null, words = result.data)
                buildCurrentGameBoard()
            }

            is Result.Error -> {
                _uiState.value =
                    getUiState().copy(
                        isLoading = false,
                        hasError = result.errorMsg,
                        words = emptyList()
                    )
            }

            is Result.Loading -> {
                _uiState.value =
                    getUiState().copy(isLoading = true)
            }
        }
    }

    private fun buildCurrentGameBoard() {
        val words = getUiState().words
        val currentWord = words[getCurrentGameState().wordIndex]

        viewModelScope.launch {
            val randomCharacters = fetchRandomCharacters.execute(currentWord.name)
            val newGameBoardState = getCurrentGameState().copy(shownCharacters = randomCharacters)
            _uiState.value = getUiState().copy(currentGameState = newGameBoardState)
        }
    }

    private fun handleCharSelection(selectedChar: Char) {
        val currentGameState = getCurrentGameState()
        val selectedChars = getCurrentGameState().charsSelected.toMutableList()
        selectedChars.add(selectedChar)
        val updatedGameState = currentGameState.copy(charsSelected = selectedChars)
        _uiState.value = getUiState().copy(currentGameState = updatedGameState)

        checkGameState()
    }

    private fun checkGameState() {
        val selectedChars = getCurrentGameState().charsSelected
        val idx = getCurrentGameState().wordIndex
        val actualWord = getUiState().words[idx]

        if (selectedChars.size == actualWord.name.length) {
            val word = selectedChars.toString()

            if (word == actualWord.name) {
                val newIdx = idx + 1
                if (newIdx < getUiState().words.size) {
                    val currentGameState = getCurrentGameState()
                    val newGameState = currentGameState.copy(
                        wordIndex = newIdx,
                        charsSelected = emptyList(),
                        shownCharacters = emptyList()
                    )
                    _uiState.value = getUiState().copy(currentGameState = newGameState)
                    buildCurrentGameBoard()
                } else {
                    _uiState.value = getUiState().copy(gameState = GameState.SUCCESS)
                }
            } else {
                _uiState.value = getUiState().copy(gameState = GameState.ERROR)
            }
        }
    }

    private fun getUiState(): WordGameUiState = _uiState.value!!

    private fun getCurrentGameState(): CurrentBoardState = getUiState().currentGameState
}