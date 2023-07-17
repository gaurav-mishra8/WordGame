package com.greenbot.wordgame.domain

class FetchRandomCharacters : UseCase {

    suspend fun execute(actualWord: String): List<Char> {
        val wordArr = actualWord.toCharArray()
        val result = mutableListOf<Char>()
        for (char in wordArr) {
            result.add(char)
        }

        for (i in 0..25) {
            result.add(i.toChar())
        }

        return result.shuffled()
    }
}