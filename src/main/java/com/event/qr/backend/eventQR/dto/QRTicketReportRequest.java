package com.event.qr.backend.eventQR.dto;

import java.util.Date;

public class QRTicketReportRequest {

    private String orderNo;
    private String ticketType;
    private String ticketStatus;
    private Date fromDate;
    private Date toDate;
    private String mobileNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return "QRTicketReportRequest{" +
                "orderNo='" + orderNo + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", ticketStatus='" + ticketStatus + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}
