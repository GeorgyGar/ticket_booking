package ru.stmLabs.ticket.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.stmLabs.ticket.dto.RouteCreateDto;
import ru.stmLabs.ticket.exception.NotFoundException;
import ru.stmLabs.ticket.model.Carrier;
import ru.stmLabs.ticket.model.Route;

import java.util.List;

@Repository
public class RouteRepository {
    private final JdbcTemplate jdbc;
    private final CarrierRepository carrierRepository;

    public RouteRepository(JdbcTemplate jdbc, CarrierRepository carrierRepository) {
        this.jdbc = jdbc;
        this.carrierRepository = carrierRepository;
    }

    public Route findById(long id) {
        String sql = "SELECT * FROM route WHERE id = ?";

        try {
            return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                long carrierId = rs.getLong("carrier_id");
                Carrier carrier = carrierRepository.findById(carrierId);

                return new Route(
                        rs.getLong("id"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        carrier,
                        rs.getInt("time")
                );
            });
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Маршрут с ID " + id + " не найден");
        }
    }

    public List<Route> findAll() {
        String sql = "SELECT * FROM route";

        return jdbc.query(sql, (rs, rowNum) -> {
            Carrier carrier = carrierRepository.findById(rs.getLong("carrier_id"));

            return new Route(
                    rs.getLong("id"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    carrier,
                    rs.getInt("time")
            );
        });
    }

    public void save(RouteCreateDto routeCreateDto) {
        String sql = "INSERT INTO route (origin, destination, carrier_id, time) VALUES (?, ?, ?, ?)";

        jdbc.update(sql, routeCreateDto.getOrigin(), routeCreateDto.getDestination(), routeCreateDto.getCarrier().getId(), routeCreateDto.getTime());
    }

    public void update(long id, RouteCreateDto routeCreateDto) {
        String sql = "UPDATE route SET origin = ?, destination = ?, carrier_id = ?, time = ? WHERE id = ?";

        jdbc.update(sql, routeCreateDto.getOrigin(), routeCreateDto.getDestination(), routeCreateDto.getCarrier().getId(), routeCreateDto.getTime(), id);
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM route WHERE id = ?";
        jdbc.update(sql, id);
    }

    public boolean existById(long id) {
        String sql = "SELECT COUNT(*) FROM route WHERE id = ?";

        return jdbc.queryForObject(sql, Long.class, id) > 0;
    }
}
