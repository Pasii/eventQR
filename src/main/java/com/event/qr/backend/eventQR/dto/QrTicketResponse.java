package com.event.qr.backend.eventQR.dto;

import com.event.qr.backend.eventQR.model.QrTicket;

public class QrTicketResponse extends Response{

    Object object;
    String qrString;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    @Override
    public String toString() {
        return "QrTicketResponse{" +
                "object=" + object +
                ", qrString='" + qrString + '\'' +
                '}';
    }
}
