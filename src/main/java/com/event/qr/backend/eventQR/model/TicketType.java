package com.event.qr.backend.eventQR.model;

public class TicketType {

    private long id;
    private String ticketType;
    private int ticketCount;
    private String variation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    @Override
    public String toString() {
        return "TicketType{" +
                "id=" + id +
                ", ticketType='" + ticketType + '\'' +
                ", ticketCount=" + ticketCount +
                ", variation='" + variation + '\'' +
                '}';
    }
}
