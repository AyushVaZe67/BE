package com.example.foodxpress;

public class UserOrder2 {
    String Name,PhoneNumber,Time;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public UserOrder2(String name, String phoneNumber, String time) {
        Name = name;
        PhoneNumber = phoneNumber;
        Time = time;
    }

    public UserOrder2() {
    }
}
