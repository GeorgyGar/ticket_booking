package ru.stmLabs.ticket.model;

public class Route {
    private long id;
    private String origin;
    private String destination;
    private Carrier carrier;
    private int time;

    public Route(long id, String origin, String destination, Carrier carrier, int time) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.carrier = carrier;
        this.time = time;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public int getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
