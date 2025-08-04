package ru.stmLabs.ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import ru.stmLabs.ticket.dto.RegisterRequest;
import ru.stmLabs.ticket.repository.UserRepository;
import ru.stmLabs.ticket.security.CustomUserDetailsService;
import ru.stmLabs.ticket.security.JwtService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private UserRepository userRepository;
    @MockBean private JwtService jwtService;
    @MockBean private AuthenticationManager authManager;
    @MockBean private PasswordEncoder encoder;
    @MockBean private CustomUserDetailsService userDetailsService;

    @Test
    void shouldRegisterUser() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFullName("Test User");
        request.setUserLogin("test");
        request.setPassword("123456");

        when(userRepository.existsByLogin("test")).thenReturn(false);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectIfPasswordTooShort() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFullName("Test");
        request.setUserLogin("short");
        request.setPassword("123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
