package ru.stmLabs.ticket.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.stmLabs.ticket.model.User;
import ru.stmLabs.ticket.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByLogin(username);
        return new CustomUserDetails(user);
    }
}
