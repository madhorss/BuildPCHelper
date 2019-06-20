package com.example.best.buildpc.Factory;

public class RAM {
    private Integer ramID;
    private String ramName;
    private Integer ramCap;
    private String ramVer;
    private Integer ramSpd;
    private Integer ramPrice;

    public RAM(Integer id,String name, Integer cap,String type,Integer spd, Integer pri){
        ramID=id;
        ramName=name;
        ramVer=type;
        ramSpd=spd;
        ramCap=cap;
        ramPrice=pri;
    }

    public Integer getRamCap() {
        return ramCap;
    }

    public void setRamCap(Integer ramCap) {
        this.ramCap = ramCap;
    }

    public Integer getRamID() {
        return ramID;
    }

    public void setRamID(Integer ramID) {
        this.ramID = ramID;
    }

    public String getRamName() {
        return ramName;
    }

    public void setRamName(String ramName) {
        this.ramName = ramName;
    }

    public String getRamVer() {
        return ramVer;
    }

    public void setRamVer(String ramVer) {
        this.ramVer = ramVer;
    }

    public Integer getRamSpd() {
        return ramSpd;
    }

    public void setRamSpd(Integer ramSpd) {
        this.ramSpd = ramSpd;
    }

    public Integer getRamPrice() {
        return ramPrice;
    }

    public void setRamPrice(Integer casePrice) {
        this.ramPrice = casePrice;
    }
}
