package com.event.qr.backend.eventQR.service;

import com.event.qr.backend.eventQR.dto.QRGeneratorResponse;
import com.event.qr.backend.eventQR.dto.QrTicketResponse;
import com.event.qr.backend.eventQR.dto.Response;
import com.event.qr.backend.eventQR.model.QrTicket;

public interface QrTicketService {
    QrTicketResponse getQrTicketDetails(String qrString);

    QRGeneratorResponse createTicket(QrTicket qrTicket);

    QrTicketResponse getQrString(String ticketId);

    Response updateTicketStatus(String tickectId, QrTicket qrTicket);

}
