package com.example.best.buildpc.Factory;

public class Motherboard {
    private Integer mbID;
    private String mbName;
    private String mbProSocket;
    private String mbChipset;
    private String mbRamVer;
    private Integer mbRamSlot;
    private Integer mbRamMaxCap;
    private Integer mbRamMaxSpd;
    private String mbSlot;
    private String mbSize;
    private String mbPin;
    private Integer mbPrice;

    public Motherboard(Integer id,
                       String name,
                       String prosoc,
                       String chip,
                       String ramver,
                       Integer ramslot,
                       Integer rMxCap,
                       Integer rammax,
                       String stslt,
                       String sz,
                       String pin,
                       Integer pri){
        mbID=id;
        mbName=name;
        mbProSocket=prosoc;
        mbChipset=chip;
        mbRamVer=ramver;
        mbRamSlot=ramslot;
        mbRamMaxCap=rMxCap;
        mbRamMaxSpd=rammax;
        mbSlot=stslt;
        mbSize=sz;
        mbPin=pin;
        mbPrice=pri;
    }

    public Integer getMbRamMaxCap() {
        return mbRamMaxCap;
    }

    public void setMbRamMaxCap(Integer mbRamMaxCap) {
        this.mbRamMaxCap = mbRamMaxCap;
    }

    public Integer getMbRamMaxSpd() {
        return mbRamMaxSpd;
    }

    public void setMbRamMaxSpd(Integer mbRamMaxSpd) {
        this.mbRamMaxSpd = mbRamMaxSpd;
    }

    public String getMbSlot() {
        return mbSlot;
    }

    public void setMbSlot(String mbSlot) {
        this.mbSlot = mbSlot;
    }

    public String getMbPin() {
        return mbPin;
    }

    public void setMbPin(String mbPin) {
        this.mbPin = mbPin;
    }

    public Integer getMbRamSlot() {
        return mbRamSlot;
    }

    public void setMbRamSlot(Integer mbRamSlot) {
        this.mbRamSlot = mbRamSlot;
    }

    public String getMbSize() {
        return mbSize;
    }

    public void setMbSize(String mbSize) {
        this.mbSize = mbSize;
    }

    public Integer getMbID() {
        return mbID;
    }

    public void setMbID(Integer mbID) {
        this.mbID = mbID;
    }

    public String getMbName() {
        return mbName;
    }

    public void setMbName(String mbName) {
        this.mbName = mbName;
    }

    public String getMbProSocket() {
        return mbProSocket;
    }

    public void setMbProSocket(String mbProSocket) {
        this.mbProSocket = mbProSocket;
    }

    public String getMbRamVer() {
        return mbRamVer;
    }

    public void setMbRamVer(String mbRamVer) {
        this.mbRamVer = mbRamVer;
    }


    public String getMbChipset() {
        return mbChipset;
    }

    public void setMbChipset(String mbChipset) {
        this.mbChipset = mbChipset;
    }

    public Integer getMbPrice() {
        return mbPrice;
    }

    public void setMbPrice(Integer mbPrice) {
        this.mbPrice = mbPrice;
    }
}
