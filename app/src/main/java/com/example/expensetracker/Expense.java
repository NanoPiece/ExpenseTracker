package com.example.expensetracker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Expense {
    private Date date;
    private String description;
    private Double amount;
    private String category;

    public Expense(String category, String description, Double amount) {
        this.date = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public Date getDate() { return this.date; }
    public String getDescription() { return this.description; }
    public String getCategory() { return this.category; }
    public  Double getAmount() { return this.amount; }
}