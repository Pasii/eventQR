package com.event.qr.backend.eventQR.dto;

import com.event.qr.backend.eventQR.model.QrTicket;
import com.event.qr.backend.eventQR.model.QrTicketReport;

import java.util.List;

public class QRTicketReportResponse extends Response{

    private List<QrTicketReport> qrTicketDetailsList;

    public List<QrTicketReport> getQrTicketDetailsList() {
        return qrTicketDetailsList;
    }

    public void setQrTicketDetailsList(List<QrTicketReport> qrTicketDetailsList) {
        this.qrTicketDetailsList = qrTicketDetailsList;
    }
}
