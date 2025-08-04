package ru.stmLabs.ticket.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.stmLabs.ticket.model.Carrier;
import ru.stmLabs.ticket.model.Route;
import ru.stmLabs.ticket.model.Ticket;
import ru.stmLabs.ticket.model.User;
import ru.stmLabs.ticket.repository.RouteRepository;
import ru.stmLabs.ticket.repository.TicketRepository;
import ru.stmLabs.ticket.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketRepository ticketRepository;
    @MockBean
    private RouteRepository routeRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldReturnAvailableTickets() throws Exception {
        Ticket ticket = new Ticket(1,
                new Route(1, "Москва", "Казань", new Carrier(1, "РЖД", ""), 100),
                LocalDateTime.now().plusDays(1), (short) 10, 1000f, 0L);

        when(ticketRepository
                .findAvailableTicketsFiltered(null, null, null, null, 0, 10))
                .thenReturn(List.of(ticket));
        when(routeRepository.findById(anyLong())).thenReturn(ticket.getRoute());

        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].origin").value("Москва"))
                .andExpect(jsonPath("$[0].carrier").value("РЖД"));
    }

    @Test
    @WithMockUser(username = "buyer", roles = {"BUYER"})
    void shouldFailPurchaseIfTicketAlreadyPurchased() throws Exception {
        when(userRepository.findByLogin("user")).thenReturn(new User(1L, "User", "user", "pass", User.UserRole.BUYER));
        when(ticketRepository.existsByIdAndNotPurchased(1L)).thenReturn(false);

        mockMvc.perform(post("/api/tickets/1/purchase"))
                .andExpect(status().isBadRequest());
    }
}
