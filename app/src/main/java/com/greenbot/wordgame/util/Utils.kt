package com.greenbot.wordgame.util

import android.content.Context
import java.io.IOException

import java.io.InputStream


fun getDataFromAsset(context: Context, fileName: String): String {
    var data: String? = ""

    try {
        val stream: InputStream = context.resources.assets.open(fileName)
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        data = String(buffer)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return data!!
}