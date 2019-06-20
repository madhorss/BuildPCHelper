package com.example.best.buildpc.Factory;

public class Case {
    private Integer caseID;
    private String caseName;
    private String caseSize;
    private Integer caseExSlot;
    private Integer caseDrive;
    private Integer caseSto35;
    private Integer caseSto25;
    private Integer casePrice;

    public Case(Integer id,
                String name,
                String sz,
                Integer ex,
                Integer csDri,
                Integer csSt3,
                Integer csSt2,
                Integer pri){
        caseID=id;
        caseName=name;
        caseSize=sz;
        caseExSlot=ex;
        caseDrive = csDri;
        caseSto35 = csSt3;
        caseSto25 = csSt2;
        casePrice=pri;
    }

    public Integer getCaseDrive() {
        return caseDrive;
    }

    public void setCaseDrive(Integer caseDrive) {
        this.caseDrive = caseDrive;
    }

    public Integer getCaseSto35() {
        return caseSto35;
    }

    public void setCaseSto35(Integer caseSto35) {
        this.caseSto35 = caseSto35;
    }

    public Integer getCaseSto25() {
        return caseSto25;
    }

    public void setCaseSto25(Integer caseSto25) {
        this.caseSto25 = caseSto25;
    }

    public Integer getCaseExSlot() {
        return caseExSlot;
    }

    public void setCaseExSlot(Integer caseExSlot) {
        this.caseExSlot = caseExSlot;
    }



    public Integer getCaseID() {
        return caseID;
    }

    public void setCaseID(Integer caseID) {
        this.caseID = caseID;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseSize() {
        return caseSize;
    }

    public void setCaseSize(String caseSize) {
        this.caseSize = caseSize;
    }

    public Integer getCasePrice() {
        return casePrice;
    }

    public void setCasePrice(Integer casePrice) {
        this.casePrice = casePrice;
    }
}
