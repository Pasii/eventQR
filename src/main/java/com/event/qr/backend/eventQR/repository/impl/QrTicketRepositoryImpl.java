package com.event.qr.backend.eventQR.repository.impl;

import com.event.qr.backend.eventQR.dto.QRTicketReportRequest;
import com.event.qr.backend.eventQR.dto.SkuTicketTypeResponse;
import com.event.qr.backend.eventQR.exception.DuplicateRecordException;
import com.event.qr.backend.eventQR.model.QrTicket;
import com.event.qr.backend.eventQR.model.QrTicketReport;
import com.event.qr.backend.eventQR.model.Sku;
import com.event.qr.backend.eventQR.model.TicketType;
import com.event.qr.backend.eventQR.repository.QrTicketRepository;
import com.event.qr.backend.eventQR.util.AppConstatnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    public void updateTicketStatus(String tickectId, String orderNo, String statusAdmitted, List<String> selectedList) throws Exception {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String sql = "update qrticket.QR_TICKET_TYPE set ticket_status = ? , admit_date = ? " +
                "where id = ? and order_no = ? and ticket_status = ? ";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {

                ps.setString(1, statusAdmitted);
                ps.setTimestamp(2,timestamp);
                ps.setString(3, selectedList.get(i));
                ps.setString(4,orderNo);
                ps.setString(5,AppConstatnt.STATUS_PENDING);

            }

            @Override
            public int getBatchSize() {
                return selectedList.size();
            }
        });

//        int count = jdbcTemplate.update(sql,new Object[]{statusAdmitted, timestamp, tickectId, orderNo, AppConstatnt.STATUS_PENDING});
//
//        if (count <= 0 ) {
//            throw new Exception();
//        }
    }

    @Override
    public List<TicketType> getTicketTypeListByOrderNo(String orderNo) {
        String sql = "select id, ticket_type,variation from qrticket.QR_TICKET_TYPE where order_no = ? and ticket_status = ?";
        List<TicketType> list = jdbcTemplate.query(sql,new TicketRowMapper(),new Object[] {orderNo, "PENDING"});
        return list;
    }

    @Override
    public QrTicket getQrTicketDetailsByOrderNo(String orderNo) {
        String sql = "SELECT id,ticket_id, order_no, mobile_no,qr_string, ticket_status FROM qrticket.QR_TICKET " +
                "where order_no = ? ";
        Object[] params = new Object[]{
                orderNo
        };
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(QrTicket.class),params);
    }

    @Override
    public Sku getSkuDetailsByTicketId(String ticketId) {

        String sql = "select s.id,s.sku_id,s.match_number,s.match,s.date,s.time,s.stadium,s.block,s.gate,s.price, t.order_no " +
                "from qrticket.SKU s " +
                "left join qrticket.QR_TICKET_TYPE tt " +
                "on s.sku_id = tt.sku " +
                "left join qrticket.QR_TICKET t " +
                "on t.order_no = tt.order_no " +
                "where t.ticket_id = ? ";

        Object[] params = new Object[]{
                ticketId
        };
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Sku.class),params);
    }

    @Override
    public List<SkuTicketTypeResponse> getSkuDetailsByOrderNo(String orderNo) {
        String sql = "select tt.id as ticket_type_id, tt.ticket_status,t.qr_string, s.id,s.sku_id,s.match_number,s.match,s.date,s.time,s.stadium,s.block,s.gate,s.price " +
                "from qrticket.SKU s " +
                "left join qrticket.QR_TICKET_TYPE tt " +
                "on s.sku_id = tt.sku " +
                "left join qrticket.QR_TICKET t " +
                "on t.order_no = tt.order_no " +
                "where t.order_no like ? ";

        Object[] params = new Object[]{
                orderNo+"%"
        };
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(SkuTicketTypeResponse.class),params);
    }

    @Override
    public List<QrTicketReport> getQrTicketReportDetails(QRTicketReportRequest request) {
        String whereClaue = "";

        String sql = "SELECT t.id, t.ticket_id, t.order_no, t.mobile_no, tt.ticket_status, tt.admit_date, " +
                "tt.ticket_type, t.seller, t.added_date  FROM qrticket.QR_TICKET t " +
                "left join qrticket.QR_TICKET_TYPE tt " +
                "on t.order_no = tt.order_no " +
                "where (t.order_no = ? OR ? IS NULL) " ;
//                "and (t.ticket_status = ? OR ? IS NULL ) " +
//                "and (t.mobile_no = ? OR ? IS NULL)" +
//                "and (t.added_date >= ? OR ? IS NULL ) " +
//                "and (t.added_date <= ? OR ? IS NULL) " +
//                "and (tt.ticket_type = ? OR ? IS NULL) ";

        Object[] params = new Object[]{
                request.getOrderNo(), request.getOrderNo(),
//                request.getTicketStatus(),request.getTicketStatus(),
//                request.getMobileNo(), request.getMobileNo(),
//                request.getFromDate(), request.getFromDate(),
//                request.getToDate(), request.getToDate(),
//                request.getTicketType(), request.getTicketType()
        };
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(QrTicketReport.class),params);
    }


    private class TicketRowMapper implements RowMapper<TicketType> {
        @Override
        public TicketType mapRow(ResultSet rs, int rowNum) throws SQLException {
            TicketType obj = new TicketType();
            obj.setId(rs.getLong("ID"));
            obj.setTicketType(rs.getString("TICKET_TYPE"));
            obj.setVariation(rs.getString("VARIATION"));
            return obj;
        }
    }
}
