package com.example.best.buildpc.Factory;

public class Processor {
    private Integer proID;
    private String proName;
    private String proSocket;
    private Double proClockSpd;
    private Double proClockMaxSpd;
    private Double proCache;
    private Integer proCore;
    private Integer proThread;
    private Integer proRamChan;
    private Integer proRamMaxSpd;
    private String proIntGpu;
    private Integer proTdp;
    private Integer proPrice;

    public Processor(Integer id,
                     String name,
                     String socket,
                     Double spd,
                     Double maxspd,
                     Double cac,
                     Integer core,
                     Integer thre,
                     Integer rmchan,
                     Integer maxRmSpd,
                     String Igpu,
                     Integer tdp,
                     Integer pri){
        proID=id;
        proName=name;
        proSocket=socket;
        proClockSpd=spd;
        proClockMaxSpd=maxspd;
        proCore=core;
        proThread=thre;
        proCache=cac;
        proRamChan=rmchan;
        proRamMaxSpd=maxRmSpd;
        proIntGpu=Igpu;
        proTdp=tdp;
        proPrice=pri;
    }

    public Integer getProID() {
        return proID;
    }

    public void setProID(Integer proID) {
        this.proID = proID;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProSocket() {
        return proSocket;
    }

    public void setProSocket(String proSocket) {
        this.proSocket = proSocket;
    }

    public Double getProClockSpd() {
        return proClockSpd;
    }

    public void setProClockSpd(Double proClockSpd) {
        this.proClockSpd = proClockSpd;
    }

    public Double getProClockMaxSpd() {
        return proClockMaxSpd;
    }

    public void setProClockMaxSpd(Double proClockMaxSpd) {
        this.proClockMaxSpd = proClockMaxSpd;
    }

    public Double getProCache() {
        return proCache;
    }

    public void setProCache(Double proCache) {
        this.proCache = proCache;
    }

    public Integer getProCore() {
        return proCore;
    }

    public void setProCore(Integer proCore) {
        this.proCore = proCore;
    }

    public Integer getProThread() {
        return proThread;
    }

    public void setProThread(Integer proThread) {
        this.proThread = proThread;
    }

    public Integer getProRamChan() {
        return proRamChan;
    }

    public void setProRamChan(Integer proRamChan) {
        this.proRamChan = proRamChan;
    }

    public Integer getProRamMaxSpd() {
        return proRamMaxSpd;
    }

    public void setProRamMaxSpd(Integer proRamMaxSpd) {
        this.proRamMaxSpd = proRamMaxSpd;
    }

    public String getProIntGpu() {
        return proIntGpu;
    }

    public void setProIntGpu(String proIntGpu) {
        this.proIntGpu = proIntGpu;
    }

    public Integer getProTdp() {
        return proTdp;
    }

    public void setProTdp(Integer proTdp) {
        this.proTdp = proTdp;
    }

    public Integer getProPrice() {
        return proPrice;
    }

    public void setProPrice(Integer proPrice) {
        this.proPrice = proPrice;
    }
}
