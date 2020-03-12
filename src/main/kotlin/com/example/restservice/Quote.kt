package com.example.restservice

class Quote(i: String, b: String, a: String) {
    private val isin: String = i
    private val bid: String = b
    private val ask: String = a

    fun getIsin(): String {
        return isin
    }

    fun getBid(): String {
        return bid
    }

    fun getAsk(): String {
        return ask
    }

}