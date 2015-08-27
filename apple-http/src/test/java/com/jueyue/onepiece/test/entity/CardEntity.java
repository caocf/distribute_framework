package com.jueyue.onepiece.test.entity;

public class CardEntity {
    private String cardid;
    private String cardname;
    private String pervalue;
    private String inprice;

    public String getCardid() {
        return cardid;
    }

    public String getCardname() {
        return cardname;
    }

    public String getInprice() {
        return inprice;
    }

    public String getPervalue() {
        return pervalue;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public void setInprice(String inprice) {
        this.inprice = inprice;
    }

    public void setPervalue(String pervalue) {
        this.pervalue = pervalue;
    }

}
