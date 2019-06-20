package com.example.best.buildpc.Factory;

public class VGA {
    private Integer vgaID;
    private String vgaName;
    private Integer vgaCoreUnit;
    private Integer vgaCoreSpd;
    private Integer vgaCoreMaxSpd;
    private Integer vgaSlotUsed;
    private String vgaInterface;
    private String vgaPin;
    private Integer vgaTdp;
    private Integer vgaSugPow;
    private Integer vgaMemSpd;
    private Integer vgaMemCap;
    private String vgaMemType;
    private Integer vgaPrice;

    public VGA(Integer id,
               String name,
               Integer cunit,
               Integer spd,
               Integer cmxspd,
               Integer slus,
               String inte,
               String pin,
               Integer tdp,
               Integer stdp,
               Integer mspd,
               Integer mcap,
               String mtype,
               Integer pri){
        vgaID = id;
        vgaName = name;
        vgaCoreUnit = cunit;
        vgaCoreSpd = spd;
        vgaCoreMaxSpd = cmxspd;
        vgaSlotUsed = slus;
        vgaInterface = inte;
        vgaPin = pin;
        vgaTdp = tdp;
        vgaSugPow = stdp;
        vgaMemSpd = mspd;
        vgaMemCap = mcap;
        vgaMemType = mtype;
        vgaPrice = pri;
    }

    public Integer getVgaCoreUnit() {
        return vgaCoreUnit;
    }

    public void setVgaCoreUnit(Integer vgaCoreUnit) {
        this.vgaCoreUnit = vgaCoreUnit;
    }

    public Integer getVgaCoreMaxSpd() {
        return vgaCoreMaxSpd;
    }

    public void setVgaCoreMaxSpd(Integer vgaCoreMaxSpd) {
        this.vgaCoreMaxSpd = vgaCoreMaxSpd;
    }

    public Integer getVgaID() {
        return vgaID;
    }

    public void setVgaID(Integer vgaID) {
        this.vgaID = vgaID;
    }

    public String getVgaName() {
        return vgaName;
    }

    public void setVgaName(String vgaName) {
        this.vgaName = vgaName;
    }

    public Integer getVgaCoreSpd() {
        return vgaCoreSpd;
    }

    public void setVgaCoreSpd(Integer vgaCoreSpd) {
        this.vgaCoreSpd = vgaCoreSpd;
    }

    public Integer getVgaSlotUsed() {
        return vgaSlotUsed;
    }

    public void setVgaSlotUsed(Integer vgaSlotUsed) {
        this.vgaSlotUsed = vgaSlotUsed;
    }

    public String getVgaInterface() {
        return vgaInterface;
    }

    public void setVgaInterface(String vgaInterface) {
        this.vgaInterface = vgaInterface;
    }

    public String getVgaPin() {
        return vgaPin;
    }

    public void setVgaPin(String vgaPin) {
        this.vgaPin = vgaPin;
    }

    public Integer getVgaTdp() {
        return vgaTdp;
    }

    public void setVgaTdp(Integer vgaTdp) {
        this.vgaTdp = vgaTdp;
    }

    public Integer getVgaSugPow() {
        return vgaSugPow;
    }

    public void setVgaSugPow(Integer vgaSugPow) {
        this.vgaSugPow = vgaSugPow;
    }

    public Integer getVgaMemSpd() {
        return vgaMemSpd;
    }

    public void setVgaMemSpd(Integer vgaMemSpd) {
        this.vgaMemSpd = vgaMemSpd;
    }

    public Integer getVgaMemCap() {
        return vgaMemCap;
    }

    public void setVgaMemCap(Integer vgaMemCap) {
        this.vgaMemCap = vgaMemCap;
    }

    public String getVgaMemType() {
        return vgaMemType;
    }

    public void setVgaMemType(String vgaMemType) {
        this.vgaMemType = vgaMemType;
    }

    public Integer getVgaPrice() {
        return vgaPrice;
    }

    public void setVgaPrice(Integer vgaPrice) {
        this.vgaPrice = vgaPrice;
    }
}

