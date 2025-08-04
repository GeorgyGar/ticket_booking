package ru.stmLabs.ticket.model;

public class Carrier {
    private long id;
    private String carrierName;
    private String phone;

    public Carrier(long id, String name, String phone) {
        this.id = id;
        this.carrierName = name;
        this.phone = phone;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getPhone() {
        return phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
