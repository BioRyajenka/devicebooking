package com.sushencev.devicebooking.integration

import com.sushencev.devicebooking.entity.DeviceInfo
import com.sushencev.devicebooking.entity.User
import com.sushencev.devicebooking.entity.repository.DeviceInfoRepo
import com.sushencev.devicebooking.entity.repository.UserRepo
import com.sushencev.devicebooking.service.devicedata.DeviceTechnicalData
import com.sushencev.devicebooking.type.DeviceFeature.BANDS_2G
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.TestPropertySourceUtils
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = ["spring.main.allow-bean-definition-overriding=true"], classes = [TestConfig::class])
@ContextConfiguration(initializers = [InMemoryDatasourceInitializer::class])
abstract class BaseIntegrationTest {
    @Autowired
    protected lateinit var context: WebApplicationContext

    protected lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var deviceInfoRepo: DeviceInfoRepo

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    protected val ADMIN_USER = "admin"
    protected val ADMIN_PASSWORD = "admin pass"

    @BeforeEach
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .defaultRequest<DefaultMockMvcBuilder>(get("/").with(
                httpBasic(ADMIN_USER, ADMIN_PASSWORD)
            )).apply<DefaultMockMvcBuilder>(springSecurity()).build()

        userRepo.save(User(ADMIN_USER, passwordEncoder.encode(ADMIN_PASSWORD)))

        initTestData()
    }

    fun initTestData() {
        deviceInfoRepo.save(DeviceInfo("iphone"))
        deviceInfoRepo.save(DeviceInfo("xiaomi"))
    }
}

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

@TestConfiguration
private class TestConfig {
    @Bean
    @Primary
    fun initialCacheContent() = mapOf(
        "iphone" to DeviceTechnicalData("iphone tech", listOf(BANDS_2G)),
        "xiaomi" to DeviceTechnicalData("xiaomi tech", listOf()),
    )
}
