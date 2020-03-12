package com.example.restservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class QuoteController {

    private val counter = AtomicLong()

    @GetMapping("/quote")
    fun createQuote(@RequestParam(value = "isin") isin: String,
                    @RequestParam(value = "ask") ask: String,
                    @RequestParam(value = "bid") bid: String): Quote? {
        if (isin.length != 12) {
            throw RuntimeException("isin length is not 12 chars")
        }

        if (!(bid.isEmpty() || ask.isEmpty())) {
            if (java.lang.Float.valueOf(bid) >= java.lang.Float.valueOf(ask)) {
                throw RuntimeException("Bid must less ask!")
            }
        }

        val quote = Quote(isin, bid, ask)
        Elvl.save(counter.incrementAndGet(), quote)
        return quote
    }

    @GetMapping("/elvl")
    fun getElvl(@RequestParam(value = "isin") isin: String?): Float? {
        return Elvl.getElvl(isin)
    }

    @GetMapping("/elvlAll")
    fun getElvlAll(): List<Map.Entry<String?, Float?>?>? {
        return Elvl.getElvlAll()
    }

}

