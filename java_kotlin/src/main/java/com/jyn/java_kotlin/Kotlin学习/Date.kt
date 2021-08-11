package com.jyn.java_kotlin.Kotlin学习

class Date {
    companion object{
        val companionObject = "companionObject测试"
        init {
            val companionInit = "companionInit测试"
        }
    }

    constructor(){
        val constructor = "constructor 测试"
    }

    init {
        val init = "init 测试"
    }
}