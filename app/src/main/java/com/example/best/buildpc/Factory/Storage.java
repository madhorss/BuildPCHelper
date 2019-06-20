package com.example.best.buildpc.Factory;

public class Storage {
    private Integer stoID;
    private String stoName;
    private Integer stoCap;
    private Integer stoRotSpd;
    private Integer stoRead;
    private Integer stoWrite;
    private String stoInterface;
    private String stoSize;
    private Integer stoPrice;


    public Storage(Integer id,
                   String name,
                   Integer cap,
                   Integer rotSpd,
                   Integer rd,
                   Integer wr,
                   String inter,
                   String sz,
                   Integer pri){
        stoID=id;
        stoName=name;
        stoCap=cap;
        stoRotSpd = rotSpd;
        stoRead=rd;
        stoWrite=wr;
        stoInterface=inter;
        stoSize=sz;
        stoPrice=pri;
    }

    public Integer getStoRotSpd() {
        return stoRotSpd;
    }

    public void setStoRotSpd(Integer stoRotSpd) {
        this.stoRotSpd = stoRotSpd;
    }

    public String getStoSize() {
        return stoSize;
    }

    public void setStoSize(String stoSize) {
        this.stoSize = stoSize;
    }

    public String getStoInterface() {
        return stoInterface;
    }

    public void setStoInterface(String stoInterface) {
        this.stoInterface = stoInterface;
    }

    public Integer getStoID() {
        return stoID;
    }

    public void setStoID(Integer stoID) {
        this.stoID = stoID;
    }

    public String getStoName() {
        return stoName;
    }

    public void setStoName(String stoName) {
        this.stoName = stoName;
    }

    public Integer getStoCap() {
        return stoCap;
    }

    public void setStoCap(Integer stoCap) {
        this.stoCap = stoCap;
    }

    public Integer getStoRead() {
        return stoRead;
    }

    public void setStoRead(Integer stoRead) {
        this.stoRead = stoRead;
    }

    public Integer getStoWrite() {
        return stoWrite;
    }

    public void setStoWrite(Integer stoWrite) {
        this.stoWrite = stoWrite;
    }

    public Integer getStoPrice() {
        return stoPrice;
    }

    public void setStoPrice(Integer stoPrice) {
        this.stoPrice = stoPrice;
    }
}
