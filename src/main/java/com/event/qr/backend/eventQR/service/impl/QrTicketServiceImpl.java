package com.event.qr.backend.eventQR.service.impl;

import com.event.qr.backend.eventQR.client.SmsApiClient;
import com.event.qr.backend.eventQR.dto.QRGeneratorResponse;
import com.event.qr.backend.eventQR.dto.QrTicketResponse;
import com.event.qr.backend.eventQR.dto.Response;
import com.event.qr.backend.eventQR.dto.SmsSendRequest;
import com.event.qr.backend.eventQR.exception.AlreadyProcessedException;
import com.event.qr.backend.eventQR.exception.DuplicateRecordException;
import com.event.qr.backend.eventQR.exception.InvalidFormatException;
import com.event.qr.backend.eventQR.exception.SendSmsException;
import com.event.qr.backend.eventQR.model.MessageElement;
import com.event.qr.backend.eventQR.model.QrTicket;
import com.event.qr.backend.eventQR.model.TicketType;
import com.event.qr.backend.eventQR.repository.QrTicketRepository;
import com.event.qr.backend.eventQR.service.QrTicketService;
import com.event.qr.backend.eventQR.util.AppConstatnt;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

            if (qrTicket != null) {
            List<TicketType> ticketTypeList = qrTicketRepository.getTicketTypeListByOrderNo(qrTicket.getOrderNo());
            qrTicket.setTicketTypeList(ticketTypeList);
            }
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

            //http://localhost:3000/qr-loader/40000001
            String urlForSendToCus = AppConstatnt.FRONTEND_BASE_URL+"qr-loader/"+ticketId;
            logger.info("__________URL send for cus :"+urlForSendToCus);

            String smsContent = "Hey Darazian! Congratulations, you have got yourself a Lunch Packet for Rs 1! " +
                    "Click here to download the QR Code and Present it to collect " +
                    "your Lunch Packet "+urlForSendToCus;

            sendSMS(qrTicket.getMobileNo(), smsContent, ticketId); //todo - temparary commented

            response.setResCode(1000);
            response.setResDescription("success");
            response.setQrString(qrString);

        } catch (DuplicateRecordException e) {
            logger.info("__________DuplicateRecordException :"+e.getMessage());
            response.setResCode(AppConstatnt.RES_CODE_1002);
            response.setResDescription(e.getMessage());

//        } catch (InvalidFormatException e) {
//            logger.info("__________InvalidFormatException :"+e.getMessage());
//            response.setResCode(AppConstatnt.RES_CODE_1003);
//            response.setResDescription(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setResCode(AppConstatnt.RES_CODE_1999);
            response.setResDescription("Platform Failure");
        }
        return response;
    }

    private void sendSMS(String mobileNo, String smsContent, String refNo) throws NoSuchAlgorithmException, InvalidFormatException, URISyntaxException, SendSmsException, JsonProcessingException {

        logger.info("__________Send SMS to : "+mobileNo);


        if (mobileNo.length() < 10) {
            throw new InvalidFormatException("Invalid mobile no");
        }

        String formattedMobileNo = "94"+mobileNo.substring(mobileNo.length() - 9);
        logger.info("______ 94 formatted mobile no :"+formattedMobileNo);



        SmsSendRequest request = new SmsSendRequest();
        MessageElement messageElement = new MessageElement();
        List<MessageElement> messageElementList = new ArrayList<>();

        messageElement.setClientRef(refNo);
        messageElement.setNumber(formattedMobileNo);
        messageElement.setMask("TEST");
        messageElement.setText(smsContent);
        messageElement.setCampaignName("Darazian campaign");

        messageElementList.add(messageElement);

        request.setMessages(messageElementList);

        logger.info(request.toString());

        SmsApiClient smsApiClient = new SmsApiClient();
        smsApiClient.sendSMS(request);

    }


    private static byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    @Override
    public QrTicketResponse getQrString(String ticketId) {
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
    public Response updateTicketStatus(String tickectId, QrTicket qrTicket) {

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

    private void checkIsAlreadyAdmitedQr(String tickectId) throws AlreadyProcessedException,EmptyResultDataAccessException  {

        String ticketStatus = qrTicketRepository.getTicketStatusBYTicketId(tickectId);
        logger.info("status :"+ticketStatus);
        if (!ticketStatus.equals(AppConstatnt.STATUS_PENDING)) {
            throw new AlreadyProcessedException(AppConstatnt.ALREADY_PREOCESSED_MESSAGE);
        }
    }


    private String generateTicketId(int noOfTickets, String orderNo) {
        Random random = new Random();
        String id = String.format("%04d", random.nextInt(10000));
        return id+orderNo;
    }

    private String getHashedQrString(String mobileNo, String orderNo, int noOfTickets) throws NoSuchAlgorithmException {

        String currentTimestamp = String.valueOf(System.currentTimeMillis());
        String qrStr = orderNo + currentTimestamp;
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
