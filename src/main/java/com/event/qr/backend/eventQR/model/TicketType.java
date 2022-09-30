package com.event.qr.backend.eventQR.model;

public class TicketType {

    private String ticketType;
    private int ticketCount;

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

    @Override
    public String toString() {
        return "TicketType{" +
                "ticketType='" + ticketType + '\'' +
                ", ticketCount='" + ticketCount + '\'' +
                '}';
    }
}
