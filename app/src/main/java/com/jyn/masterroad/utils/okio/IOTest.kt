package com.jyn.masterroad.utils.okio

import java.io.*

class IOTest {
    val path = ""

    fun input() {
        val inputStream: InputStream = FileInputStream(path)
        val char = inputStream.read().toChar()

        val reader = InputStreamReader(inputStream)
        val reader2 = FileReader(path)
        reader.read()
        reader2.read()

        val bufferReader = BufferedReader(reader)
        bufferReader.readLine()
    }
}