package com.example.restservice

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ElvlTest {
    private var app: Elvl? = null
    private var quote1: Quote? = null
    private var quote2: Quote? = null
    private var quote3: Quote? = null
    private var quoteEmptyBid: Quote? = null

    @BeforeEach
    fun setUp() {
        app = Elvl()

        (app!!.getHistory() as MutableMap<Long, Quote>).clear()
        (app!!.getElvl() as MutableMap<String, Float>).clear()
        quote1 = Quote("RU000A0JX0J2", 100.2f.toString(), 101.9f.toString())
        quote2 = Quote("RU000A0JX0J2", 100.5f.toString(), 101.9f.toString())
        quote3 = Quote("RU000A0JX0J2", 100.5f.toString(), 101.8f.toString())
        quoteEmptyBid = Quote("RU000A0JX0J2", "", 101.9f.toString())
    }

    @Test
    fun getElvl() {
        app!!.save(1L, quote1!!)
        val result = Elvl.getElvl("RU000A0JX0J2")
        Assertions.assertEquals(100.2f, result)
        app!!.save(2L, quote2!!)
        Assertions.assertEquals(100.5f, Elvl.getElvl("RU000A0JX0J2"))
    }

    @Test
    fun getElvlAndBidBiggerElvl() {
        app!!.save(1L, quote1!!)
        Assertions.assertEquals(100.2f, Elvl.getElvl("RU000A0JX0J2"))
        app!!.save(2L, quote2!!)
        Assertions.assertEquals(100.5f, Elvl.getElvl("RU000A0JX0J2"))
    }

    @Test
    fun getElvlAndElvlMissing() {
        app!!.save(1L, quote1!!)
        Assertions.assertEquals(100.2f, Elvl.getElvl("RU000A0JX0J2"))
    }

    @Test
    fun elvlEmpty() {
        Assertions.assertEquals(null, Elvl.getElvl("RU000A0JX0J2"))
    }

    @Test
    fun getElvlAndElvlMissingAndBidMissing() {
        app!!.save(1L, quoteEmptyBid!!)
        Assertions.assertEquals(101.9f, Elvl.getElvl("RU000A0JX0J2"))
    }

    @Test
    fun getElvlAndBidMissing() {
        app!!.save(1L, quoteEmptyBid!!)
        Assertions.assertEquals(101.9f, Elvl.getElvl("RU000A0JX0J2"))
        app!!.save(2L, quote2!!)
        Assertions.assertEquals(101.9f, Elvl.getElvl("RU000A0JX0J2"))
    }

    @Test
    fun getElvlAndBidMissingAndAskLessElvl() {
        app!!.save(1L, quoteEmptyBid!!)
        Assertions.assertEquals(101.9f, Elvl.getElvl("RU000A0JX0J2"))
        app!!.save(2L, quote3!!)
        Assertions.assertEquals(101.8f, Elvl.getElvl("RU000A0JX0J2"))
    }


    @Test
    fun save() {
        app!!.save(1L, quote1!!)
        app!!.save(2L, quote2!!)
        app!!.save(3L, quote3!!)
        app!!.save(4L, quoteEmptyBid!!)
        Assertions.assertEquals(4, app!!.getHistory().size)
    }
}