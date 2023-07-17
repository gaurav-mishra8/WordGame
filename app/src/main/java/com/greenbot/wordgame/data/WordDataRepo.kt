package com.greenbot.wordgame.data

import com.greenbot.wordgame.domain.WordGame

interface WordDataRepo {

    fun fetchWordsList(): List<WordGame>
}