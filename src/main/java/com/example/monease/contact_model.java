package com.example.monease;

public class contact_model {
    String date,type,name;
    Float amount;

    public contact_model(String date, Float amount, String type, String name) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public Float getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
