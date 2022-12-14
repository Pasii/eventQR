package com.event.qr.backend.eventQR.repository.impl;

import com.event.qr.backend.eventQR.exception.DuplicateRecordException;
import com.event.qr.backend.eventQR.model.QrTicket;
import com.event.qr.backend.eventQR.model.TicketType;
import com.event.qr.backend.eventQR.repository.QrTicketRepository;
import com.event.qr.backend.eventQR.util.AppConstatnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QrTicketRepositoryImpl implements QrTicketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public QrTicket getQrTicketDetailsByQrString(String qrString) throws Exception{

        String sql = "SELECT id,ticket_id, order_no, mobile_no,qr_string, ticket_status FROM qrticket.QR_TICKET " +
                "where qr_string = ? ";
        Object[] params = new Object[]{
                qrString
        };
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(QrTicket.class),params);
    }

    @Override
    public void addTicketDetails(QrTicket qrTicket) throws SQLException, DuplicateRecordException {

        int existingRecordCount = 0;

        String sql0 = "select count(*) from qrticket.QR_TICKET where order_no = ? ";

        existingRecordCount = jdbcTemplate.queryForObject(sql0,Integer.class,qrTicket.getOrderNo());

        if (existingRecordCount != 0) {
            throw new DuplicateRecordException("Duplicate OrderNo");
        }


        String sql = "Insert into qrticket.QR_TICKET (ticket_id,order_no,mobile_no, no_of_tickets, qr_string, ticket_status)\n" +
                "values (?,?,?,?,?,?) ";

        Object[] params = new Object[] {
                qrTicket.getTicketId(),
                qrTicket.getOrderNo(),
                qrTicket.getMobileNo(),
                qrTicket.getNoOfTickets(),
                qrTicket.getQrString(),
                "PENDING"
        };

        jdbcTemplate.update(sql,params);
    }

    @Override
    public String getQrStringByTicketId(String ticketId) throws SQLException{

        String sql = "select qr_string from qrticket.QR_TICKET where ticket_id = ? ";

        return jdbcTemplate.queryForObject(sql,String.class,ticketId);
    }

    @Override
    public String getTicketStatusBYTicketId(String tickectId) {

        String sql = "select ticket_status from qrticket.QR_TICKET where ticket_id = ? ";
        return jdbcTemplate.queryForObject(sql,String.class,tickectId);
    }

    @Override
    public void updateTicketStatus(String tickectId, String orderNo, String statusAdmitted) {

        String sql = "update qrticket.QR_TICKET set ticket_status = ? where " +
                "ticket_id = ? and order_no = ? and ticket_status = ?";

        jdbcTemplate.update(sql,new Object[]{statusAdmitted, tickectId, orderNo, AppConstatnt.STATUS_PENDING});
    }

    @Override
    public List<TicketType> getTicketTypeListByOrderNo(String orderNo) {
        String sql = "select ticket_type, count(*) as ticket_count from qrticket.QR_TICKET_TYPE where order_no = ? " +
                "group by ticket_type ";
        List<TicketType> list = jdbcTemplate.query(sql,new TicketRowMapper(),new Object[] {orderNo});
        return list;
    }


    private class TicketRowMapper implements RowMapper<TicketType> {
        @Override
        public TicketType mapRow(ResultSet rs, int rowNum) throws SQLException {
            TicketType obj = new TicketType();
            obj.setTicketType(rs.getString("TICKET_TYPE"));
            obj.setTicketCount(rs.getInt("TICKET_COUNT"));
            return obj;
        }
    }
}
