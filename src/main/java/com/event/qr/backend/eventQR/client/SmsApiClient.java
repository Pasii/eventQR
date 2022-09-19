package com.event.qr.backend.eventQR.client;

import com.event.qr.backend.eventQR.dto.SmsSendRequest;
import com.event.qr.backend.eventQR.dto.SmsSendResponse;
import com.event.qr.backend.eventQR.exception.SendSmsException;
import com.event.qr.backend.eventQR.model.MessageElement;
import com.event.qr.backend.eventQR.service.impl.QrTicketServiceImpl;
import com.event.qr.backend.eventQR.util.AppConstatnt;
import com.event.qr.backend.eventQR.util.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class SmsApiClient {

    @Autowired
    RestTemplate restTemplate;

    Logger logger = LogManager.getLogger(SmsApiClient.class);

    public void sendSMS(SmsSendRequest request) throws URISyntaxException, NoSuchAlgorithmException, SendSmsException, JsonProcessingException {

        String pwd = AppConstatnt.SMS_API_PWD;
        String digestStr = CommonUtil.getMd5HashedString(pwd);

        String url = "https://richcommunication.dialog.lk/api/sms/send";

        String formattedTime = CommonUtil.getIsoFormattedTimeString();
        logger.info("Date format "+formattedTime);

        HttpHeaders headers = new HttpHeaders();

        headers.set("USER", AppConstatnt.SMS_API_USER);
        headers.set("DIGEST", digestStr);
        headers.set("CREATED",formattedTime);
        headers.set("Content-Type", "application/json");

        HttpEntity<SmsSendRequest> requestEntity = new HttpEntity<>(request, headers);

        restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.POST, requestEntity, String.class);

        String jsonInput= response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info(jsonInput);
        SmsSendResponse resObj = objectMapper.readValue(jsonInput, new TypeReference<SmsSendResponse>(){});

        logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&response "+resObj.toString());
        if (resObj.getResultCode() != 0) {
            throw new SendSmsException("Sending sms failed");
        }

    }

}
