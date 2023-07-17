package com.greenbot.wordgame.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.greenbot.wordgame.domain.WordGame
import com.greenbot.wordgame.util.getDataFromAsset
import org.json.JSONException

interface LocalDataSource {
    fun fetchWordsList(): List<WordGame>
}


class LocalDataSourceImpl(
    private val context: Context,
    private val gson: Gson
) : LocalDataSource {

    override fun fetchWordsList(): List<WordGame> {
        val wordGames = mutableListOf<WordGame>()
        try {
            val data = getDataFromAsset(context, FILE_NAME)
            val typeToken = object : TypeToken<List<WordGame>>() {}.type

            wordGames.add(gson.fromJson<WordGame>(data, typeToken))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return wordGames
    }

    companion object {
        const val FILE_NAME = "words.json"
    }
}