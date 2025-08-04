package ru.stmLabs.ticket.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.stmLabs.ticket.dto.CarrierCreateDto;
import ru.stmLabs.ticket.exception.NotFoundException;
import ru.stmLabs.ticket.model.Carrier;

import java.util.List;

@Repository
public class CarrierRepository {
    private final JdbcTemplate jdbc;

    public CarrierRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Carrier findById(long id) {
        String sql = "SELECT * FROM carrier WHERE id = ?";

        try {
            return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new Carrier(
                            rs.getLong("id"),
                            rs.getString("carrier_name"),
                            rs.getString("phone")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Перевозчик с ID " + id + " не найден");
        }
    }

    public List<Carrier> findAll() {
        String sql = "SELECT * FROM carrier";

        return jdbc.query(sql, (rs, rowNum) ->
                new Carrier(
                        rs.getLong("id"),
                        rs.getString("carrier_name"),
                        rs.getString("phone")
                )
        );
    }

    public void save(CarrierCreateDto carrierCreateDto) {
        String sql = "INSERT INTO carrier (carrier_name, phone) VALUES (?, ?)";

        jdbc.update(sql, carrierCreateDto.getCarrierName(), carrierCreateDto.getPhone());
    }

    public void update(long id, CarrierCreateDto carrierCreateDto) {
        String sql = "UPDATE carrier SET carrier_name = ?, phone = ? WHERE id = ?";

        jdbc.update(sql, carrierCreateDto.getCarrierName(), carrierCreateDto.getPhone(), id);
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM carrier WHERE id = ?";
        jdbc.update(sql, id);
    }

    public boolean existById(long id) {
        String sql = "SELECT COUNT(*) FROM carrier WHERE id = ?";

        return jdbc.queryForObject(sql, Long.class, id) > 0;
    }
}
