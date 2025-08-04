package ru.stmLabs.ticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.stmLabs.ticket.dto.CarrierCreateDto;
import ru.stmLabs.ticket.dto.ErrorResponse;
import ru.stmLabs.ticket.dto.RegisterRequest;
import ru.stmLabs.ticket.dto.RouteCreateDto;
import ru.stmLabs.ticket.model.*;
import ru.stmLabs.ticket.repository.*;

@Tag(name = "Администратор", description = "Методы доступные администратору")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final CarrierRepository carrierRepository;
    private final RouteRepository routeRepository;
    private final TicketRepository ticketRepository;

    public AdminController(UserRepository userRepository, PasswordEncoder encoder, CarrierRepository carrierRepository,
                           RouteRepository routeRepository,
                           TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.carrierRepository = carrierRepository;
        this.routeRepository = routeRepository;
        this.ticketRepository = ticketRepository;
    }

    @Operation(
            summary = "Создать перевозчика",
            description = "Создает нового перевозчика (требуются права ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description="Перевозчик создан"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/carriers")
    public ResponseEntity<?> createCarrier(@RequestBody CarrierCreateDto carrierCreateDto) {
        carrierRepository.save(carrierCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Обновить перевозчика",
            description = "Обновляет перевозчика (требуются права ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description="Перевозчик обновлен"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/carriers/{id}")
    public ResponseEntity<?> updateCarrier(@PathVariable long id, @RequestBody CarrierCreateDto carrierCreateDto) {
        carrierRepository.update(id, carrierCreateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удалить перевозчика",
            description = "Удаляет перевозчика (требуются права ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description="Перевозчик удален"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/carriers/{id}")
    public ResponseEntity<?> deleteCarrier(@PathVariable long id) {
        carrierRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Создать маршрут",
            description = "Создает новый маршрут (требуются права ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description="Маршрут создан"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/routes")
    public ResponseEntity<?> createRoute(@RequestBody RouteCreateDto routeCreateDto) {
        routeRepository.save(routeCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Обновить маршрут",
            description = "Обновляет маршрут (требуются права ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description="Маршрут обновлен"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/routes/{id}")
    public ResponseEntity<?> updateRoute(@PathVariable long id, @RequestBody RouteCreateDto routeCreateDto) {
        routeRepository.update(id, routeCreateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удалить маршрут",
            description = "Удаляет маршрут (требуются права ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description="Маршрут удален"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/routes/{id}")
    public ResponseEntity<?> deleteRoute(@PathVariable long id) {
        routeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Создать билет",
            description = "Создает новый билет (требуются права ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description="Билет создан"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/tickets")
    public ResponseEntity<?> createTicket(@RequestBody Ticket ticket) {
        ticketRepository.create(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Регистрация администратора",
            description = "Добавляет нового администратора (требуются права ADMIN)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description="Администратор добавлен"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        User admin = new User(
                0L,
                request.getFullName(),
                request.getUserLogin(),
                encoder.encode(request.getPassword()),
                User.UserRole.ADMIN
        );
        userRepository.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
