package com.jyn.masterroad.utils.okio

import android.os.Environment
import okio.buffer
import okio.sink
import okio.source
import java.io.File

class OkioTest {

    fun readAndWrite() {
        //读流程
        /**
         * 旧写法
         * val readSource = Okio.buffer(Okio.source(fileSource))
         * val writeSink = Okio.buffer(Okio.sink(fileWrite))
         */
        val fileSource = File(Environment.getExternalStorageState(), "test")
        val readSource = fileSource.source().buffer()
        val readArray = ByteArray(1024)
        readSource.read(readArray)
        readSource.close()

        //写流程
        val fileWrite = File(Environment.getExternalStorageState(), "test")
        val writeSink = fileWrite.sink().buffer()
        val writeArray = ByteArray(1024)
        writeSink.write(writeArray)
        writeSink.close()
    }
}