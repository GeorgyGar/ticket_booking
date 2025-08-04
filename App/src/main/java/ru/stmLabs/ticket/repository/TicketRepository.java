package ru.stmLabs.ticket.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.stmLabs.ticket.model.Route;
import ru.stmLabs.ticket.model.Ticket;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepository {
    private final JdbcTemplate jdbc;
    private final RouteRepository routeRepository;

    public TicketRepository(JdbcTemplate jdbc, RouteRepository routeRepository) {
        this.jdbc = jdbc;
        this.routeRepository = routeRepository;
    }

    public List<Ticket> findAvailableTicketsFiltered(LocalDateTime fromDateTime,
                                                     String origin,
                                                     String destination,
                                                     String carrier,
                                                     int page,
                                                     int size) {
        String sql = "SELECT * FROM ticket t " +
                "JOIN route r ON t.route_id = r.id " +
                "JOIN carrier c ON r.carrier_id = c.id " +
                "WHERE t.purchased = false";
        List<Object> params = new ArrayList<>();

        if (fromDateTime != null) {
            sql += " AND t.date_time >= ?";
            params.add(Timestamp.valueOf(fromDateTime));
        }
        if (origin != null) {
            sql += " AND r.origin LIKE ?";
            params.add("%" + origin + "%");
        }
        if (destination != null) {
            sql += " AND r.destination LIKE ?";
            params.add("%" + destination + "%");
        }
        if (carrier != null) {
            sql += " AND c.carrier_name LIKE ?";
            params.add("%" + carrier + "%");
        }
        sql += " ORDER BY t.date_time ASC LIMIT ? OFFSET ?";
        params.add(size);
        params.add(page * size);

        return jdbc.query(sql.toString(),params.toArray(), (rs, rowNum) -> {
            long routeId = rs.getLong("route_id");
            Route route = routeRepository.findById(routeId);

            return new Ticket(
                    rs.getLong("id"),
                    route,
                    rs.getTimestamp("date_time").toLocalDateTime(),
                    rs.getShort("seat_number"),
                    rs.getFloat("price"),
                    rs.getLong("user_id")
            );
        });
    }

    public boolean existsByIdAndNotPurchased(long ticketId) {
        String sql = "SELECT COUNT(*) FROM ticket WHERE id = ? AND purchased = false";
        return jdbc.queryForObject(sql, Long.class, ticketId) > 0;
    }

    public boolean purchaseTicket(long ticketId, long userId) {
        String sql = "UPDATE ticket SET purchased = true, user_id = ? WHERE id = ? AND purchased = false";
        return jdbc.update(sql, userId, ticketId) == 1;
    }

    public List<Ticket> findTicketsByUserId(long userId) {
        String sql = "SELECT * FROM ticket WHERE user_id = ?";

        return jdbc.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            long routeId = rs.getLong("route_id");
            Route route = routeRepository.findById(routeId);

            return new Ticket(
                    rs.getLong("id"),
                    route,
                    rs.getTimestamp("date_time").toLocalDateTime(),
                    rs.getShort("seat_number"),
                    rs.getFloat("price"),
                    rs.getLong("user_id")
            );
        });
    }

    public void create(Ticket ticket) {
        String sql = "INSERT INTO ticket (route_id, date_time, seat_number, price, purchased, user_id) VALUES (?, ?, ?, ?, false, NULL)";
        jdbc.update(sql,
                ticket.getRoute().getId(),
                Timestamp.valueOf(ticket.getDateTime()),
                ticket.getSeatNumber(),
                ticket.getPrice()
        );
    }

}