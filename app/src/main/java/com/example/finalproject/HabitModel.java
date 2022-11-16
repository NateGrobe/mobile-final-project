package com.example.finalproject;

public class HabitModel {

    private int id;
    private String name, habitType;
    private boolean isActive;

    //Constructors


    public HabitModel(int id, String name, String habitType) {
        this.id = id;
        this.name = name;
        this.habitType = habitType;
//        this.isActive = isActive;
    }

    //toString to print as one line

    @Override
    public String toString() {
        return "HabitModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", habitType='" + habitType +
//                '\'' +
//                ", isActive=" + isActive +
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

//    public boolean isActive() {
//        return isActive;
//    }
//
//    public void setActive(boolean active) {
//        isActive = active;
//    }
}
