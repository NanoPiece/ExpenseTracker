package com.example.expensetracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "amount")
    private Double amount;
    @ColumnInfo(name = "category")
    private String category;
    @Ignore
    private boolean isChecked; // Used for multi-selection UI only

    public Expense(String category, String description, Double amount) {
        this.date = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        this.category = category;
        this.description = description;
        this.amount = Math.round(amount * 100.0)/100.0;
        this.isChecked = false;
    }

    public int getUid() { return this.uid; }
    public Date getDate() { return this.date; }
    public String getDescription() { return this.description; }
    public String getCategory() { return this.category; }
    public Double getAmount() { return this.amount; }
    public Boolean isChecked() { return this.isChecked; }

    public void setUid(int uid) { this.uid = uid; }
    public void setDate(Date date) {this.date = date; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setChecked(Boolean checked) { this.isChecked = checked; }
}
