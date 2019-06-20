package com.example.best.buildpc.Factory;


public class build {
    private Integer buildID;
    private String buildName;
    private Integer mbID;
    private Integer proID;
    private Integer ramID;
    private Integer vgaID;
    private Integer stoID;
    private Integer psuID;
    private Integer caseID;
    private String Date;
    private Integer totPrice;

    public build(Integer bid,String nam, Integer mid, Integer pid, Integer rid, Integer vid, Integer sid, Integer psid, Integer cid, String dt, Integer pri){
        buildID=bid;
        buildName=nam;
        mbID=mid;
        proID=pid;
        ramID=rid;
        vgaID=vid;
        stoID=sid;
        psuID=psid;
        caseID=cid;
        Date=dt;
        totPrice=pri;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getBuildID() {
        return buildID;
    }

    public void setBuildID(Integer buildID) {
        this.buildID = buildID;
    }

    public Integer getMbID() {
        return mbID;
    }

    public void setMbID(Integer mbID) {
        this.mbID = mbID;
    }

    public Integer getProID() {
        return proID;
    }

    public void setProID(Integer proID) {
        this.proID = proID;
    }

    public Integer getRamID() {
        return ramID;
    }

    public void setRamID(Integer ramID) {
        this.ramID = ramID;
    }

    public Integer getVgaID() {
        return vgaID;
    }

    public void setVgaID(Integer vgaID) {
        this.vgaID = vgaID;
    }

    public Integer getStoID() {
        return stoID;
    }

    public void setStoID(Integer stoID) {
        this.stoID = stoID;
    }

    public Integer getPsuID() {
        return psuID;
    }

    public void setPsuID(Integer psuID) {
        this.psuID = psuID;
    }

    public Integer getCaseID() {
        return caseID;
    }

    public void setCaseID(Integer caseID) {
        this.caseID = caseID;
    }

    public Integer getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(Integer totPrice) {
        this.totPrice = totPrice;
    }
}
