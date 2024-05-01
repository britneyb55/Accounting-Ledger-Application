package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AccountingLedger
{

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("kk:mm:ss");

    private LocalDate date ;
    private LocalTime time ;


    String description;
    String vendor;
    double amount;


    public AccountingLedger( LocalDate date, LocalTime time ,  String description, String vendor, double amount)
    {
        this.date = date;//LocalDate.parse(LocalDate.now().format(DATE_FORMAT));
        this.time = time;//LocalTime.parse(LocalTime.now().format(TIME_FORMAT));
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
