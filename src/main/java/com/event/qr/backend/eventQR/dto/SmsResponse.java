package com.event.qr.backend.eventQR.dto;

public class SmsResponse {

    private String clientRef;
    private String serverRef;
    private int resultCode;
    private String resultDesc;

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getServerRef() {
        return serverRef;
    }

    public void setServerRef(String serverRef) {
        this.serverRef = serverRef;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    @Override
    public String toString() {
        return "SmsResponse{" +
                "clientRef='" + clientRef + '\'' +
                ", serverRef='" + serverRef + '\'' +
                ", resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                '}';
    }
}
