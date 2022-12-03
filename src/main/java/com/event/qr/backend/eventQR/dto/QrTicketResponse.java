package com.event.qr.backend.eventQR.dto;

import com.event.qr.backend.eventQR.model.QrTicket;

import java.util.List;

public class QrTicketResponse extends Response{

    List<Object> objectList;
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

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    @Override
    public String toString() {
        return "QrTicketResponse{" +
                "objectList=" + objectList +
                ", object=" + object +
                ", qrString='" + qrString + '\'' +
                '}';
    }
}
