package ru.stmLabs.ticket.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.stmLabs.ticket.model.User;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public User findByLogin(String login) {
        String sql = "SELECT * FROM app_user WHERE user_login = ?";

        return jdbc.queryForObject(sql, new Object[]{login}, (rs, rowNum) -> {
            return new User(
                    rs.getLong("id"),
                    rs.getString("full_name"),
                    rs.getString("user_login"),
                    rs.getString("password"),
                    User.UserRole.valueOf(rs.getString("role"))
            );

        });
    }

    public boolean existsByLogin(String login) {
        String sql = "SELECT COUNT(*) FROM app_user WHERE user_login = ?";

        return jdbc.queryForObject(sql, Long.class, login) > 0;
    }

    public void save(User user) {
        String sql = "INSERT INTO app_user (user_login, password, full_name, role) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, user.getUserLogin(), user.getPassword(), user.getFullName() ,user.getRole().name());
    }
}
