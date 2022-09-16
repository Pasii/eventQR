package com.event.qr.backend.eventQR.dto;

public class Response {

    private int resCode;
    private String resDescription;

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResDescription() {
        return resDescription;
    }

    public void setResDescription(String resDescription) {
        this.resDescription = resDescription;
    }

    @Override
    public String toString() {
        return "Response{" +
                "resCode=" + resCode +
                ", resDescription=" + resDescription +
                '}';
    }



}
