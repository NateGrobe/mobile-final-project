package com.example.finalproject;

import java.util.Date;

public class HabitModel {

    private int id;
    private String name, habitType, startDate;
    private int missedDays, totalDays;



    //Constructors


    public HabitModel(int id, String name, String habitType, String startDate,int missedDays, int totalDays) {
        this.id = id;
        this.name = name;
        this.habitType = habitType;
        this.startDate = startDate;
        this.missedDays = missedDays;
        this.totalDays = totalDays;
    }

    //toString to print as one line

    @Override
    public String toString() {
        return "HabitModel{" +
                "id=" + id +
                ", name='" + name +
                '\'' +
                ", habitType='" + habitType +
                '\'' +
                ", startDate=" + startDate +
                '\'' +
                ", missedDays=" + missedDays +
                '\'' +
                ", totalDays=" + totalDays +
                '}';
    }


    // Getters and Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getHabitType() {
        return habitType;
    }
    public void setHabitType(String habitType) {
        this.habitType = habitType;
    }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public int getMissedDays() { return missedDays; }
    public void setMissedDays(int missedDays) { this.missedDays = missedDays; }

    public int getTotalDays() { return totalDays; }
    public void setTotalDays(int totalDays) { this.totalDays = totalDays; }
}
