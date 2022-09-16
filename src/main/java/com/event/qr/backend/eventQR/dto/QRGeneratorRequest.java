package com.event.qr.backend.eventQR.dto;

public class QRGeneratorRequest {


    private String mobileNo;
    private String orderNo;
    private int ticketsNumb;


    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getTicketsNumb() {
        return ticketsNumb;
    }

    public void setTicketsNumb(int ticketsNumb) {
        this.ticketsNumb = ticketsNumb;
    }

    @Override
    public String toString() {
        return "QRGeneratorRequest{" +
                "mobileNo='" + mobileNo + '\'' +
                ", memberName='" + orderNo + '\'' +
                ", ticketsNumb=" + ticketsNumb +
                '}';
    }
}
