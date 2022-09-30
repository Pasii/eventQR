package com.event.qr.backend.eventQR.repository;

import com.event.qr.backend.eventQR.exception.DuplicateRecordException;
import com.event.qr.backend.eventQR.model.QrTicket;
import com.event.qr.backend.eventQR.model.TicketType;

import java.sql.SQLException;
import java.util.List;

public interface QrTicketRepository {
    QrTicket getQrTicketDetailsByQrString(String qrString) throws Exception;

    void addTicketDetails(QrTicket qrTicket) throws SQLException, DuplicateRecordException;

    String getQrStringByTicketId(String ticketId) throws SQLException;

    String getTicketStatusBYTicketId(String tickectId);

    void updateTicketStatus(String tickectId, String orderNo, String statusAdmitted);

    List<TicketType> getTicketTypeListByOrderNo(String orderNo);
}
