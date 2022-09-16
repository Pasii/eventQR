package com.event.qr.backend.eventQR.dto;

public class QRGeneratorResponse extends Response {

    private String qrString;

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

    @Override
    public String toString() {
        return "QRGeneratorResponse{" +
                "qrString='" + qrString + '\'' +
                '}';
    }
}
