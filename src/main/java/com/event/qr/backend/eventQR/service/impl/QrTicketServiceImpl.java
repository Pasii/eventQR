package com.event.qr.backend.eventQR.service.impl;

import com.event.qr.backend.eventQR.dto.QRGeneratorResponse;
import com.event.qr.backend.eventQR.dto.QrTicketResponse;
import com.event.qr.backend.eventQR.dto.Response;
import com.event.qr.backend.eventQR.exception.AlreadyProcessedException;
import com.event.qr.backend.eventQR.exception.DuplicateRecordException;
import com.event.qr.backend.eventQR.model.QrTicket;
import com.event.qr.backend.eventQR.repository.QrTicketRepository;
import com.event.qr.backend.eventQR.service.QrTicketService;
import com.event.qr.backend.eventQR.util.AppConstatnt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class QrTicketServiceImpl implements QrTicketService {

    Logger logger = LogManager.getLogger(QrTicketServiceImpl.class);

    @Autowired
    QrTicketRepository qrTicketRepository;

    @Override
    public QrTicketResponse getQrTicketDetails(String qrString) {

        logger.info("__________QrTicketServiceImpl : getQrTicketDetails...");
        QrTicketResponse response = new QrTicketResponse();
        try {

            QrTicket qrTicket = qrTicketRepository.getQrTicketDetailsByQrString(qrString);
            response.setObject(qrTicket);
            response.setResCode(1000);
            response.setResDescription("Success");

        } catch (EmptyResultDataAccessException e) {
            logger.info("__________No records found for id...");
            response.setResCode(1001);
            response.setResDescription("No records found");
        } catch (Exception e) {
            logger.error(e);
            response.setResCode(1999);
            response.setResDescription("Platform Failure");
        }
        return response;
    }

    @Override
    public QRGeneratorResponse createTicket(QrTicket qrTicket) {

        String qrString = null;
        String ticketId = null;
        QRGeneratorResponse response = new QRGeneratorResponse();

        try {

            //validate request parameters

            //generate id from ticket details
            ticketId = generateTicketId(qrTicket.getNoOfTickets(),qrTicket.getOrderNo());
            qrTicket.setTicketId(ticketId);

            //generate qr string from ticket details
            qrString = getHashedQrString(qrTicket.getMobileNo(),qrTicket.getOrderNo(),qrTicket.getNoOfTickets());
            logger.info("__________"+qrString+"_______"+ qrString.length());
            qrTicket.setQrString(qrString);

            qrTicketRepository.addTicketDetails(qrTicket);
            response.setResCode(1000);
            response.setResDescription("success");
            response.setQrString(qrString);

        } catch (DuplicateRecordException e) {
            logger.info("__________DuplicateRecordException :"+e.getMessage());
            response.setResCode(AppConstatnt.RES_CODE_1002);
            response.setResDescription(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setResCode(AppConstatnt.RES_CODE_1999);
            response.setResDescription("Platform Failure");
        }
        return response;
    }

    @Override
    public QrTicketResponse getQrString(int ticketId) {
        String qrString = null;
        QrTicketResponse response = new QrTicketResponse();
        try {

            qrString = qrTicketRepository.getQrStringByTicketId(ticketId);
            response.setResCode(AppConstatnt.RES_CODE_SUCCESS);
            response.setResDescription("success");
            response.setQrString(qrString);

        } catch (EmptyResultDataAccessException e) {
            logger.info("__________No records found for id...");
            response.setResCode(AppConstatnt.RES_CODE_1001);
            response.setResDescription("No records found");

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setResCode(AppConstatnt.RES_CODE_1999);
            response.setResDescription("Platform Failure");
        }
        return response;
    }

    @Override
    public Response updateTicketStatus(int tickectId, QrTicket qrTicket) {

        Response response= new Response();
        try {

            //check is already admited qr
            checkIsAlreadyAdmitedQr(tickectId);

            qrTicketRepository.updateTicketStatus(tickectId,qrTicket.getOrderNo(),AppConstatnt.STATUS_ADMITTED);

            response.setResCode(AppConstatnt.RES_CODE_SUCCESS);
            response.setResDescription(AppConstatnt.TICKET_ADMITED_SUCCESS_MESSAGE);

        } catch (EmptyResultDataAccessException e) {
            logger.info("__________No records found for ticketId...");
            response.setResCode(AppConstatnt.RES_CODE_1001);
            response.setResDescription("No records found");
        }catch (AlreadyProcessedException e) {
            logger.info("__________Ticket is already processed...");
            response.setResCode(1003);
            response.setResDescription(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setResCode(AppConstatnt.RES_CODE_1999);
            response.setResDescription("Platform Failure");
        }
        return response;
    }

    private void checkIsAlreadyAdmitedQr(int tickectId) throws AlreadyProcessedException,EmptyResultDataAccessException  {

        String ticketStatus = qrTicketRepository.getTicketStatusBYTicketId(tickectId);
        logger.info("status :"+ticketStatus);
        if (!ticketStatus.equals(AppConstatnt.STATUS_PENDING)) {
            throw new AlreadyProcessedException(AppConstatnt.ALREADY_PREOCESSED_MESSAGE);
        }
    }


    private String generateTicketId(int noOfTickets, String orderNo) {
        return noOfTickets+orderNo;
    }

    private String getHashedQrString(String mobileNo, String orderNo, int noOfTickets) throws NoSuchAlgorithmException {

        String currentTimestamp = String.valueOf(System.currentTimeMillis());
        String qrStr = mobileNo + orderNo + noOfTickets + currentTimestamp;
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(
                qrStr.getBytes(StandardCharsets.UTF_8));
        String sha3Hex = bytesToHex(hashbytes);

        return sha3Hex;

    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
