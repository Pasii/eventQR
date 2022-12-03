package com.event.qr.backend.eventQR.model;

import java.util.Date;

public class Sku {

    private Long id;
    private String skuId;
    private String matchNumber;
    private String match;
    private String date;
    private String time;
    private String stadium;
    private String block;
    private String gate;
    private String price;
    private String orderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "id=" + id +
                ", skuId='" + skuId + '\'' +
                ", matchNumber='" + matchNumber + '\'' +
                ", match='" + match + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", stadium='" + stadium + '\'' +
                ", block='" + block + '\'' +
                ", gate='" + gate + '\'' +
                ", price='" + price + '\'' +
                ", orderNo='" + orderNo + '\'' +
                '}';
    }
}
