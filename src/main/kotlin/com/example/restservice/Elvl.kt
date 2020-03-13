package com.example.restservice

import java.util.*
import java.util.stream.Collectors
import kotlin.Float as Float1

class Elvl {

    companion object {
        private val historyMap: MutableMap<Long, Quote> = HashMap()
        private val elvlMap: MutableMap<String, Float1> = HashMap()

        private fun calculateElvl(quote: Quote): Float1 {
            var elvl: Float1? = null
            val isin: String
            val bid: Float1
            val ask: Float1
            try {
                isin = quote.getIsin()
                elvl = elvlMap[isin]
                bid = Float.valueOf(quote.getBid())
                ask = Float.valueOf(quote.getAsk())
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
            } catch (e: NumberFormatException) {
                if (quote.getBid().isEmpty()) {
                    return Float.valueOf(quote.getAsk())
                }
                e.printStackTrace()
            }
            return elvl!!.toFloat()
        }

        fun save(id: Long, quote: Quote) {
            historyMap[id] = quote
            elvlMap[quote.getIsin()] = calculateElvl(quote)
        }

        fun getElvlAll(): List<Map.Entry<String?, Float1?>>? {
            return elvlMap.entries.stream().collect(Collectors.toList())
        }

        fun getElvl(isin: String?): Float1? {
            return elvlMap[isin]
        }
    }

    fun getHistory(): Map<Long, Quote> {
        return historyMap
    }

    fun getElvl(): Map<String, Float1> {
        return elvlMap
    }

    fun save(id: Long, quote: Quote) {
        historyMap[id] = quote
        elvlMap[quote.getIsin()] = calculateElvl(quote)
    }
}




