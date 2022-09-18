package com.event.qr.backend.eventQR.Controller;

import com.event.qr.backend.eventQR.dto.QRGeneratorRequest;
import com.event.qr.backend.eventQR.dto.QRGeneratorResponse;
import com.event.qr.backend.eventQR.dto.QrTicketResponse;
import com.event.qr.backend.eventQR.dto.Response;
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
    @CrossOrigin(origins = "http://localhost:8080/eventQR")
    @GetMapping("/ticket/{qrString}")
    public QrTicketResponse getQrTicketDetails(@PathVariable String qrString) {
        logger.info("__________getQrTicketDetails : qrId :"+qrString);
        QrTicketResponse qrTicketResponse = qrTicketService.getQrTicketDetails(qrString);
        logger.info("____________________getQrTicketDetails : response : "+qrTicketResponse.toString());
        return qrTicketResponse;
    }

    //return qr string according to cus reference
    @GetMapping("/ticket/qr/{ticketId}")
    public QrTicketResponse getQrString(@PathVariable int ticketId) {
        logger.info("__________getQrString : ticketId :"+ticketId);
        QrTicketResponse qrTicketResponse = qrTicketService.getQrString(ticketId);
        return qrTicketResponse;
    }

    //set falg for scanned qr
    @PutMapping("/ticket/status/{ticketId}")
    public Response updateTicketStatus(@PathVariable(value = "ticketId") int tickectId ,@RequestBody QrTicket qrTicket) {

        logger.info("__________ticketId : "+tickectId);
        logger.info("__________updateTicketStatus : request :"+qrTicket);
        Response response = qrTicketService.updateTicketStatus(tickectId,qrTicket);
        return response;
    }
}
