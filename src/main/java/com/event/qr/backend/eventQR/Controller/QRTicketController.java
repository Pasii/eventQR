package com.event.qr.backend.eventQR.Controller;

import com.event.qr.backend.eventQR.dto.*;
import com.event.qr.backend.eventQR.model.QrTicket;
import com.event.qr.backend.eventQR.service.QrTicketService;
import com.event.qr.backend.eventQR.service.impl.QrTicketServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class QRTicketController {

    Logger logger = LogManager.getLogger(QRTicketController.class);

    @Autowired
    QrTicketService qrTicketService;

    @GetMapping("/testApi")
    public String testApi() {
        return "This API is working......";
    }

//    @CrossOrigin(origins = "http://eventqr-env.eba-ygicmmbp.us-west-2.elasticbeanstalk.com/eventQR")
    @PostMapping("/ticket")
    public Response createQrTicket(@RequestBody QrTicket qrTicket) {
        logger.info("__________createQrTicket : request : "+qrTicket.toString());
        QRGeneratorResponse response = qrTicketService.createTicket(qrTicket);

        return response;
    }

    /**
     * This method get qr ticket details by qrString
     * @param qrString
     * @return
     */
    //@CrossOrigin(origins = "http://eventqr-env.eba-ygicmmbp.us-west-2.elasticbeanstalk.com/eventQR")
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/ticket/{qrString}")
    public QrTicketResponse getQrTicketDetails(@PathVariable String qrString) {
        logger.info("__________getQrTicketDetails : qrId :"+qrString);
        QrTicketResponse qrTicketResponse = qrTicketService.getQrTicketDetails(qrString);
        logger.info("____________________getQrTicketDetails : response : "+qrTicketResponse.toString());
        return qrTicketResponse;
    }

    //return qr string according to cus reference
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/ticket/qr/{ticketId}")
    public QrTicketResponse getQrString(@PathVariable String ticketId) {
        logger.info("__________getQrString : ticketId :"+ticketId);
        QrTicketResponse qrTicketResponse = qrTicketService.getQrString(ticketId);
        return qrTicketResponse;
    }

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/ticket/sku/{orderNo}")
    public QrTicketResponse getSkuDetails(@PathVariable String orderNo) {
        logger.info("__________getQrString : ticketId :"+orderNo);
        QrTicketResponse qrTicketResponse = qrTicketService.getSkuDetails(orderNo);
        return qrTicketResponse;
    }

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/ticket/order/{orderNo}")
    public QrTicketResponse getQRTicketDetailsByOrderNo(@PathVariable String orderNo) {
        logger.info("__________getQrString : order no :"+orderNo);
        QrTicketResponse qrTicketResponse = qrTicketService.getQrStringByOrderNo(orderNo);
        return qrTicketResponse;
    }

    //set falg for scanned qr
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/ticket/status/{ticketId}")
    public Response updateTicketStatus(@PathVariable(value = "ticketId") String tickectId ,@RequestBody QrTicket qrTicket) {

        logger.info("__________ticketId : "+tickectId);
        logger.info("__________updateTicketStatus : request :"+qrTicket);
        Response response = qrTicketService.updateTicketStatus(tickectId,qrTicket);
        return response;
    }

//    @PostMapping("/report/ticket")
//    public QRTicketReportResponse getQrTicketReportDetails(@RequestBody QRTicketReportRequest request) {
//        logger.info("__________Request : "+request.toString());
//        QRTicketReportResponse response = qrTicketService.getTicketReportDetails(request);
//        return response;
//    }
}
