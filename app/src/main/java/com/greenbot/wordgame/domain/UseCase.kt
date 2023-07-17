package com.greenbot.wordgame.domain

import com.greenbot.wordgame.data.WordDataRepo

interface UseCase

class FetchWordsUseCase(
    private val wordRepo: WordDataRepo
) : UseCase {

    fun execute(): Result<List<WordGame>> {
        val res = try {
            val result = wordRepo.fetchWordsList()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e.message ?: "")
        }

        return res
    }
}