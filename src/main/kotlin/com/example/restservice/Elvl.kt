package com.example.restservice

import java.util.*
import java.util.stream.Collectors
import kotlin.Float as Float1

class Elvl {
    companion object {
        private var history: MutableMap<Long, Quote> = HashMap()
        private val elvl: MutableMap<String, Float1> = HashMap()

        private fun calculateElvl(quote: Quote): Float1 {
            var isin: String?
            var elvl: Float1?
            val bid: Float1
            val ask: Float1
            try {
                isin = quote.getIsin()
                elvl = this.elvl[isin]
                bid = java.lang.Float.valueOf(quote.getBid())
                ask = java.lang.Float.valueOf(quote.getAsk())
                if (elvl != null) {
                    if (bid > elvl) {
                        return bid
                    }
                    if (ask < elvl) {
                        return ask
                    }
                } else {
                    return bid
                }
                return elvl
            } catch (e: NumberFormatException) {
                if (quote.getBid()!!.isEmpty()) {
                    return java.lang.Float.valueOf(quote.getAsk())
                }
                e.printStackTrace()
            }
            throw NullPointerException()
        }

        fun save(id: Long, quote: Quote) {
            history[id] = quote
            elvl[quote.getIsin().toString()] = calculateElvl(quote)
        }

        fun getElvlAll(): List<Map.Entry<String?, Float1?>>? {
            return elvl.entries.stream().collect(Collectors.toList())
        }

        fun getElvl(isin: String?): Float1? {
            return elvl.get(isin)
        }


    }

    fun getHistory(): Map<Long, Quote>? {
        return history
    }

    fun getElvl(): Map<String, Float1>? {
        return elvl
    }

    fun getElvl(isin: String?): Float1? {
        return elvl.get(isin)
    }

    fun save(id: Long, quote: Quote) {
        history[id] = quote
        elvl[quote.getIsin().toString()] = calculateElvl(quote)
    }

    private operator fun Float1?.get(isin: String?): Float1? {
        return elvl[isin]
    }
}




