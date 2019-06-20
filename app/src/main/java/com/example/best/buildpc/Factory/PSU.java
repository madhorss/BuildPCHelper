package com.example.best.buildpc.Factory;

public class PSU {
    private Integer psuID;
    private String psuName;
    private String psuType;
    private String psuRank;
    private Integer psuVolt;
    private String psuPin;
    private String psuSize;
    private Integer psuPrice;

    public PSU(Integer id,
               String name,
               String type,
               String rank,
               Integer volt,
               String pin,
               String sz,
               Integer pri){
        psuID=id;
        psuName=name;
        psuType=type;
        psuRank=rank;
        psuVolt=volt;
        psuPin=pin;
        psuSize=sz;
        psuPrice=pri;

    }

    public String getPsuPin() {
        return psuPin;
    }

    public void setPsuPin(String psuPin) {
        this.psuPin = psuPin;
    }

    public String getPsuSize() {
        return psuSize;
    }

    public void setPsuSize(String psuSize) {
        this.psuSize = psuSize;
    }

    public Integer getPsuID() {
        return psuID;
    }

    public void setPsuID(Integer psuID) {
        this.psuID = psuID;
    }

    public String getPsuName() {
        return psuName;
    }

    public void setPsuName(String psuName) {
        this.psuName = psuName;
    }

    public String getPsuType() {
        return psuType;
    }

    public void setPsuType(String psuType) {
        this.psuType = psuType;
    }

    public String getPsuRank() {
        return psuRank;
    }

    public void setPsuRank(String psuRank) {
        this.psuRank = psuRank;
    }

    public Integer getPsuVolt() {
        return psuVolt;
    }

    public void setPsuVolt(Integer psuVolt) {
        this.psuVolt = psuVolt;
    }

    public Integer getPsuPrice() {
        return psuPrice;
    }

    public void setPsuPrice(Integer psuPrice) {
        this.psuPrice = psuPrice;
    }
}
