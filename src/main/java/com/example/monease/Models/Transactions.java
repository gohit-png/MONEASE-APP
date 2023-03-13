package com.example.monease.Models;

public class Transactions
{
    public String  date;
    public String name;
    public String type;
    public String amount;

    public Transactions(String amount, String date, String name , String type) {
        this.amount = amount;
        this.date = date;
        this.name = name;
        this.type= type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
