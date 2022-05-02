package com.sushencev.devicebooking.integration

import com.sushencev.devicebooking.entity.User
import com.sushencev.devicebooking.entity.repository.UserRepo
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.support.TestPropertySourceUtils
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

private class InMemoryDatasourceInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            applicationContext,
            "spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            // disable repeatable migrations for tests
            "spring.flyway.repeatable-sql-migration-prefix=DISABLED",
        )
    }
}

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [InMemoryDatasourceInitializer::class])
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class TestAuth {
    @Autowired
    protected lateinit var context: WebApplicationContext

    protected lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    private val ADMIN_USER = "admin"
    private val ADMIN_PASSWORD = "admin pass"

    @BeforeEach
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity()).build()

        userRepo.save(User(ADMIN_USER, passwordEncoder.encode(ADMIN_PASSWORD)))
    }

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
