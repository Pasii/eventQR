package com.event.qr.backend.eventQR.util;

import com.event.qr.backend.eventQR.client.SmsApiClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CommonUtil {

    static Logger logger = LogManager.getLogger(CommonUtil.class);

    public static String getIsoFormattedTimeString( ) {
        // Input
        Date date = new Date(System.currentTimeMillis());
        // Conversion
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        String formattedTime = sdf.format(date);

        return formattedTime;
    }

    public static String getMd5HashedString(String pwd) throws NoSuchAlgorithmException {

        //generate MD5 hash string from pwd
        String original = pwd;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        logger.info("original:" + original);
        String digestStr = sb.toString();
        logger.info("digested(hex):" + digestStr);

        return digestStr;
    }

}
