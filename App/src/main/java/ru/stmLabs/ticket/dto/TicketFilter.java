package ru.stmLabs.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema (description = "Фильтры для поиска билетов")
public class TicketFilter {
    private LocalDateTime fromDateTime;
    private String origin;
    private String destination;
    private String carrier;

    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(LocalDateTime fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
}
