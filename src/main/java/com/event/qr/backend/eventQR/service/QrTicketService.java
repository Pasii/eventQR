package com.event.qr.backend.eventQR.service;

import com.event.qr.backend.eventQR.dto.*;
import com.event.qr.backend.eventQR.model.QrTicket;

public interface QrTicketService {
    QrTicketResponse getQrTicketDetails(String qrString);

    QRGeneratorResponse createTicket(QrTicket qrTicket);

    QrTicketResponse getQrString(String ticketId);

    Response updateTicketStatus(String tickectId, QrTicket qrTicket);

    QrTicketResponse getQrStringByOrderNo(String orderNo);

    QrTicketResponse getSkuDetails(String orderNo);

    QRTicketReportResponse getTicketReportDetails(QRTicketReportRequest request);
}
