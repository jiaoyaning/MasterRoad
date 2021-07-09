package com.jyn.masterroad.kotlin.coroutines

import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce

/*
 * 破解 Kotlin 协程(9) - Channel 篇
 * https://mp.weixin.qq.com/s/8j74bn9x0-gFmZxa6k6GwA
 */
class ChannelTest {

    companion object {
        const val TAG = "Channel"
    }

    fun simple() = MainScope().launch {
        /**
         * 如果 consumer 不 receive，producer 里面的第一个 send 就给挂起了
         */
        val channel = Channel<Int>()
        val producer = GlobalScope.launch {
            var i = 0
            while (i < 5) {
                i++
                LogUtils.tag(TAG).i("send -> before $i")
                channel.send(i) //这其实也是一个挂起函数
                LogUtils.tag(TAG).i("send -> after $i")
                delay(1000)
            }
        }

        val consumer = GlobalScope.launch {
            while (true) {
                delay(2000)
                val element = channel.receive()
                LogUtils.tag(TAG).i("receive --> element $element")
            }
        }

        producer.join()
        consumer.join()
    }

    fun iterator() = MainScope().launch {
        val channel = Channel<Int>()
        val iterator = channel.iterator()
        val producer = GlobalScope.launch {
            while (iterator.hasNext()) {
                val element = iterator.next()
                LogUtils.tag(TAG).i("iterator.next --> element $element")
                delay(2000)
            }
        }

        val consumer = GlobalScope.launch {
            var i = 0
            while (i < 5) {
                i++
                LogUtils.tag(TAG).i("send -> before $i")
                channel.send(i) //这其实也是一个挂起函数
                LogUtils.tag(TAG).i("send -> after $i")
                delay(1000)
            }
        }
        producer.join()
        consumer.join()
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun produceAndActor() = MainScope().launch {
        /**
         * ReceiveChannel 和 SendChannel 都是 Channel 的父接口，
         * 前者定义了 receive，后者定义了 send，Channel 也因此既可以 receive 又可以 send。
         */
        val receiveChannel: ReceiveChannel<Int> = GlobalScope.produce {
            var i = 0
            while (i < 5) {
                delay(1000)
                i++
                LogUtils.tag(TAG).i("produce -> before $i")
                send(1)
                LogUtils.tag(TAG).i("produce -> after $i")
            }
        }
        LogUtils.tag(TAG).i("receiveChannel -> receive ${receiveChannel.receive()}")

        val sendChannel = GlobalScope.actor<Int> {
            while (true) {
                val element = receive()
                LogUtils.tag(TAG).i("actor --> element $element")
                delay(2000)
            }
        }
        LogUtils.tag(TAG).i("sendChannel -> before")
        sendChannel.send(0)
        LogUtils.tag(TAG).i("sendChannel -> after")
    }
}