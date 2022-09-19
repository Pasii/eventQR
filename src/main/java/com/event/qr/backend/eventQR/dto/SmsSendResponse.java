package com.event.qr.backend.eventQR.dto;

import com.event.qr.backend.eventQR.model.MessageElement;

import java.util.List;

public class SmsSendResponse {

    private int resultCode;
    private String resultDesc;
    private List<SmsResponse> messages;


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

    public List<SmsResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<SmsResponse> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "SmsSendResponse{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", messages=" + messages +
                '}';
    }
}
