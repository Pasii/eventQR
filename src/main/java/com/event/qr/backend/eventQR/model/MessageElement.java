package com.event.qr.backend.eventQR.model;

public class MessageElement {

    private String clientRef;
    private String number;
    private String mask;
    private String text;
    private String campaignName;

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    @Override
    public String toString() {
        return "MessageElement{" +
                "clientRef='" + clientRef + '\'' +
                ", number='" + number + '\'' +
                ", mask='" + mask + '\'' +
                ", text='" + text + '\'' +
                ", campaignName='" + campaignName + '\'' +
                '}';
    }
}
