package com.event.qr.backend.eventQR.dto;

import com.event.qr.backend.eventQR.model.Sku;

public class SkuTicketTypeResponse extends Sku {

    private Long ticketTypeId;
    private String ticketStatus;
    private String qrString;

    public Long getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(Long ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    @Override
    public String toString() {
        return "SkuTicketTypeResponse{" +
                "ticketTypeId=" + ticketTypeId +
                ", ticketStatus='" + ticketStatus + '\'' +
                ", qrString='" + qrString + '\'' +
                '}';
    }
}
