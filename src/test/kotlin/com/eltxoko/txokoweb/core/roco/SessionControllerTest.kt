package com.eltxoko.txokoweb.core.roco

import com.eltxoko.txokoweb.core.roco.service.SessionService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
internal class SessionControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
) {

    @MockBean
    private lateinit var sessionService: SessionService

    // Purpose: check constraint annotations

    @Test
    fun `Successful session create request`() {
        mockMvc.perform(
            post("/api/admin/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "openDate": "2030-01-01",
                            "pairLimit": 3
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `Session create request with past date`() {
        mockMvc.perform(
            post("/api/admin/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "openDate": "2000-01-01",
                            "pairLimit": 3
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Session create request with wrong date format`() {
        mockMvc.perform(
            post("/api/admin/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "openDate": "20300101",
                            "pairLimit": 3
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Session create request with invalid date value`() {
        mockMvc.perform(
            post("/api/admin/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "openDate": "2030-02-30",
                            "pairLimit": 3
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isBadRequest)

        mockMvc.perform(
            post("/api/admin/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "openDate": "2030-00-00",
                            "pairLimit": 3
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Session create request with non-positive limit value`() {
        mockMvc.perform(
            post("/api/admin/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "openDate": "2030-01-01",
                            "pairLimit": 0
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isBadRequest)

        mockMvc.perform(
            post("/api/admin/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "openDate": "2030-01-01",
                            "pairLimit": -1
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isBadRequest)
    }
}