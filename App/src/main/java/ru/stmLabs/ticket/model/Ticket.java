package ru.stmLabs.ticket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Ticket {
    @JsonIgnore
    private long id;

    private Route route;
    private LocalDateTime dateTime;
    private short seatNumber;
    private float price;
    private long userId;

    public Ticket(long id, Route route, LocalDateTime dateTime, short seatNumber, float price, long userId) {
        this.id = id;
        this.route = route;
        this.dateTime = dateTime;
        this.seatNumber = seatNumber;
        this.price = price;
        this.userId = userId;
    }

    public Route getRoute() {
        return route;
    }

    public short getSeatNumber() {
        return seatNumber;
    }

    public float getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
