package com.event.qr.backend.eventQR.service;

import com.event.qr.backend.eventQR.dto.QrTicketResponse;
import com.event.qr.backend.eventQR.dto.Response;
import com.event.qr.backend.eventQR.model.QrTicket;

public interface QrTicketService {
    QrTicketResponse getQrTicketDetails(String qrString);

    Response createTicket(QrTicket qrTicket);

    QrTicketResponse getQrString(int ticketId);

    Response updateTicketStatus(int tickectId, QrTicket qrTicket);
}
