package ru.stmLabs.ticket.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.stmLabs.ticket.dto.CarrierCreateDto;
import ru.stmLabs.ticket.repository.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private UserRepository userRepository;
    @MockBean private PasswordEncoder encoder;
    @MockBean private CarrierRepository carrierRepository;
    @MockBean private RouteRepository routeRepository;
    @MockBean private TicketRepository ticketRepository;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminCanCreateCarrier() throws Exception {
        CarrierCreateDto dto = new CarrierCreateDto();
        dto.setCarrierName("NewCarrier");
        dto.setPhone("+123456789");

        mockMvc.perform(post("/api/admin/carriers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "buyer", roles = {"BUYER"})
    void buyerCannotCreateCarrier() throws Exception {
        CarrierCreateDto dto = new CarrierCreateDto();
        dto.setCarrierName("BlockedCarrier");
        dto.setPhone("+123456789");

        mockMvc.perform(post("/api/admin/carriers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }
}