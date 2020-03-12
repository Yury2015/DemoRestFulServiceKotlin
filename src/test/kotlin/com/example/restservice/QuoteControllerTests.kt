package com.example.restservice

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class QuoteControllerTests {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    @Throws(Exception::class)
    fun noParamQuoteShouldReturnError() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/quote")).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().`is`(400))
    }

    @Test
    fun paramQuoteShouldExceptionBidMustLessAsk() {
        try {
            mockMvc!!.perform(MockMvcRequestBuilders.get("/quote")
                    .param("isin", "RU000A0JX0J2")
                    .param("bid", "105.2")
                    .param("ask", "101.9"))
            Assertions.fail<Any>("Exception \"Bid must less ask!\" is not thrown")
        } catch (e: java.lang.Exception) {
            val errorMessage = "Bid must less ask!"
            val message = e.message!!.substring(e.message!!.length - errorMessage.length)
            Assertions.assertEquals(errorMessage, message)
        }
    }

    @Test
    fun paramQuoteShouldExceptionIsinLengthIsNot12Chars() {
        try {
            mockMvc!!.perform(MockMvcRequestBuilders.get("/quote")
                    .param("isin", "RU000A0JX0J")
                    .param("bid", "100.2")
                    .param("ask", "101.9"))
            Assertions.fail<Any>("Exception \"isin length is not 12 chars\" is not thrown")
        } catch (e: java.lang.Exception) {
            val errorMessage = "isin length is not 12 chars"
            val message = e.message!!.substring(e.message!!.length - errorMessage.length)
            Assertions.assertEquals(errorMessage, message)
        }
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun paramQuoteShouldReturnOk() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/quote")
                .param("isin", "RU000A0JX0J2")
                .param("bid", "100.2")
                .param("ask", "101.9"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("{\"isin\":\"RU000A0JX0J2\",\"bid\":\"100.2\",\"ask\":\"101.9\"}"))
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun paramElvlShouldReturnOk() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/elvl")
                .param("isin", "RU000A0JX0J2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("100.2"))
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun paramElvlAllShouldReturnOk() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/elvlAll"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("[{\"RU000A0JX0J2\":100.2}]"))
    }

}