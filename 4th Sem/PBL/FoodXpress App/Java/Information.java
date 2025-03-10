package com.example.foodxpress;

public class Information {
    private String item1,item2,item3;

    public Information(String item1, String item2, String item3) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }

    public Information() {
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }
}
