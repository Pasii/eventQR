package com.event.qr.backend.eventQR.repository;

import com.event.qr.backend.eventQR.exception.DuplicateRecordException;
import com.event.qr.backend.eventQR.model.QrTicket;

import java.sql.SQLException;

public interface QrTicketRepository {
    QrTicket getQrTicketDetailsByQrString(String qrString) throws Exception;

    void addTicketDetails(QrTicket qrTicket) throws SQLException, DuplicateRecordException;

    String getQrStringByTicketId(int ticketId) throws SQLException;
}
