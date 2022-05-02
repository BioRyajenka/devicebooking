package com.sushencev.devicebooking.integration

import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class TestAuth : BaseIntegrationTest() {

    @Test
    fun `wrong password should fail request`() {
        mockMvc.perform(
            post("/api/v1/devices/577d0988-ef0c-44e3-9467-555c5e5106e0/book")
                .with(httpBasic(ADMIN_USER, "wrong pass"))
        ).andExpect(status().isUnauthorized).andReturn()
    }

    @Test
    fun `correct password should not fail request`() {
        mockMvc.perform(
            post("/api/v1/devices/577d0988-ef0c-44e3-9467-555c5e5106e0/book")
                .with(httpBasic(ADMIN_USER, ADMIN_PASSWORD))
        ).andExpect(status().`is`(IsNot(IsEqual(401)))).andReturn()
    }
}
