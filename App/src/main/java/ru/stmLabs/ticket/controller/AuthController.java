package ru.stmLabs.ticket.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.stmLabs.ticket.dto.*;
import ru.stmLabs.ticket.model.User;
import ru.stmLabs.ticket.repository.UserRepository;
import ru.stmLabs.ticket.security.CustomUserDetailsService;
import ru.stmLabs.ticket.security.JwtService;

@Tag(name = "Аутентификация", description = "API для регистрации и аутентификации пользователей")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;


    public AuthController(UserRepository userRepository,
                          PasswordEncoder encoder,
                          JwtService jwtService,
                          AuthenticationManager authManager,
                          CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    @Operation(
            summary = "Аутентификация пользователя",
            description = "Аутентификация пользователя и возврат токенов JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Аутентификация прошла успешно"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Неверные учетные данные"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        String accessToken = jwtService.generateAccessToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(accessToken));
    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Создает нового пользователя с ролью BUYER"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Успеашня регистрация"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Логин уже занят")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (request.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Пароль должен содержать не менее 6 символов");
        }
        if (userRepository.existsByLogin(request.getUserLogin())) {
            return ResponseEntity.badRequest().body("Логин занят");
        }

        User user = new User(
                0L,
                request.getFullName(),
                request.getUserLogin(),
                encoder.encode(request.getPassword()),
                User.UserRole.BUYER
        );

        userRepository.save(user);

        return ResponseEntity.ok("Пользователь создан");
    }
}
