package org.example.petstore.controller.api;

import lombok.SneakyThrows;
import org.example.petstore.dto.account.AccountInfoDto;
import org.example.petstore.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    AccountService accountService;

    @BeforeEach
    void setUp() {
        Mockito.reset(accountService);
    }

    @Test
    @SneakyThrows
    void testGetAccountInfo() {
        AccountInfoDto mockResult = new AccountInfoDto("John", "Doe",
                "testUser", "john@example.com");
        when(accountService.getAccountInfoConsideringAccess(null)).thenReturn(mockResult);

        mockMvc.perform(get("/api/account/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(accountService, times(1)).getAccountInfoConsideringAccess(null);
    }
}