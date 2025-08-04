package ru.stmLabs.ticket.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.stmLabs.ticket.dto.*;
import ru.stmLabs.ticket.model.Route;
import ru.stmLabs.ticket.model.User;
import ru.stmLabs.ticket.repository.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Билеты", description = "API для работы с транспортными билетами")
@Validated
public class TicketController {
    private final TicketRepository ticketRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    public TicketController(TicketRepository ticketRepository,
                            RouteRepository routeRepository,
                            UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "Получить доступные билеты",
            description = "Возвращает список билетов с возможностью фильтрации и пагинации"
    )
    @GetMapping
    public List<TicketDto> getAvailableTickets(
            @Parameter(description = "Фильтр по дате/времени отправления")
            @RequestParam(required = false) LocalDateTime fromDateTime,

            @Parameter(description = "Фильтр по пункту отправления (частичное совпадение)")
            @RequestParam(required = false) String origin,

            @Parameter(description = "Фильтр по пункту назначения (частичное совпадение)")
            @RequestParam(required = false) String destination,

            @Parameter(description = "Фильтр по перевозчику (частичное совпадение)")
            @RequestParam(required = false) String carrier,

            @Parameter(description = "Номер страницы (начиная с 0)")
            @RequestParam(defaultValue = "0") @Min(0) int page,

            @Parameter(description = "Размер страницы (1-100)")
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {

        return ticketRepository.findAvailableTicketsFiltered(
                fromDateTime, origin,
                destination, carrier,
                page, size
        ).stream().map(ticket -> {
            Route route = routeRepository.findById(ticket.getRoute().getId());
            return new TicketDto(
                    ticket.getId(),
                    route.getOrigin(),
                    route.getDestination(),
                    route.getCarrier().getCarrierName(),
                    ticket.getDateTime(),
                    ticket.getSeatNumber(),
                    ticket.getPrice()
            );
        }).toList();
    }

    @Operation(
            summary = "Купить билет",
            description = "Покупка билета по его идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Билет успешно куплен"),
            @ApiResponse(responseCode = "400", description = "Билет не найден или уже куплен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Требуется авторизация",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/{id}/purchase")
    public ResponseEntity<?> purchaseTicket(
            @Parameter(description = "ID билета") @PathVariable long id,
            Authentication authentication) {

        String login = authentication.getName();
        User user = userRepository.findByLogin(login);

        if (!ticketRepository.existsByIdAndNotPurchased(id)) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Ошибка покупки",
                            Map.of("error", "Билет не найден или уже куплен")));
        }

        boolean success = ticketRepository.purchaseTicket(id, user.getId());

        if (success) {
            return ResponseEntity.ok(Map.of("message", "Билет успешно куплен"));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Ошибка покупки",
                            Map.of("error", "Не удалось купить билет")));
        }
    }

    @Operation(
            summary = "Получить купленные билеты",
            description = "Возвращает список билетов, купленных текущим пользователем"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список купленных билетов"),
            @ApiResponse(responseCode = "401", description = "Требуется авторизация",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasRole('BUYER')")
    @GetMapping("/my")
    public ResponseEntity<?> getMyTickets(Authentication authentication) {
        String login = authentication.getName();
        User user = userRepository.findByLogin(login);

        List<TicketDto> tickets = ticketRepository.findTicketsByUserId(user.getId())
                .stream()
                .map(ticket -> {
                    Route route = routeRepository.findById(ticket.getRoute().getId());
                    return new TicketDto(
                            ticket.getId(),
                            route.getOrigin(),
                            route.getDestination(),
                            route.getCarrier().getCarrierName(),
                            ticket.getDateTime(),
                            ticket.getSeatNumber(),
                            ticket.getPrice()
                    );
                }).toList();

        return ResponseEntity.ok(tickets);
    }
}