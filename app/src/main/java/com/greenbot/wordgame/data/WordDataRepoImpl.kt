package com.greenbot.wordgame.data

import com.greenbot.wordgame.domain.WordGame

class WordDataRepoImpl(
    private val localDataSource: LocalDataSource
) : WordDataRepo {

    override fun fetchWordsList(): List<WordGame> {
        return localDataSource.fetchWordsList()
    }
}