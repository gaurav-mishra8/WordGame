package com.greenbot.wordgame.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.greenbot.wordgame.R

class WordGameActivity : AppCompatActivity() {

    private val viewModel by viewModels<WordGameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_word_game)

        viewModel.uiState.observe(this) {

        }

        viewModel.accept(ViewActions.LoadGame)
    }
}