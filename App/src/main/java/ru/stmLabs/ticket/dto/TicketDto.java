package ru.stmLabs.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Информация о билете")
public class TicketDto {
    @Schema(description = "ID билета", example = "1")
    private long id;

    @Schema(description = "Пункт отправления", example = "Москва")
    private String origin;

    @Schema(description = "Пункт назначения", example = "Санкт-Петербург")
    private String destination;

    @Schema(description = "Название перевозчика", example = "РЖД")
    private String carrier;

    @Schema(description = "Дата и время отправления", example = "2023-12-31T08:00:00")
    private LocalDateTime dateTime;

    @Schema(description = "Номер места", example = "15")
    private short seatNumber;

    @Schema(description = "Цена билета", example = "1500.50")
    private float price;

    public TicketDto(long id, String origin, String destination, String carrier, LocalDateTime dateTime, short seatNumber, float price) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.carrier = carrier;
        this.dateTime = dateTime;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public short getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(short seatNumber) {
        this.seatNumber = seatNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
