package com.example.RecipeHub.client.dtos;

public class BankDTO {
    private String bankcode;
    private String name;
    private int displayorder;
    private int pmcid;
    private long minamount;
    private long maxamount;

    public BankDTO(String bankcode, String name, int displayorder, int pmcid, long minamount, long maxamount) {
        this.bankcode = bankcode;
        this.name = name;
        this.displayorder = displayorder;
        this.pmcid = pmcid;
        this.minamount = minamount;
        this.maxamount = maxamount;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(int displayorder) {
        this.displayorder = displayorder;
    }

    public int getPmcid() {
        return pmcid;
    }

    public void setPmcid(int pmcid) {
        this.pmcid = pmcid;
    }

    public long getMinamount() {
        return minamount;
    }

    public void setMinamount(long minamount) {
        this.minamount = minamount;
    }

    public long getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(long maxamount) {
        this.maxamount = maxamount;
    }
}
