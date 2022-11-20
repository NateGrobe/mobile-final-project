package com.example.finalproject;

public class Foods {
    private String foodName;
    private double foodCalories;


    public Foods(String name,double calories){
        this.foodName = name;
        this.foodCalories = calories;

    }

    public  Foods(){

    }

    public String getFoodName() {
        return foodName;
    }

    public double getFoodCalories() {
        return foodCalories;
    }



    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodCalories(double foodCalories) {
        this.foodCalories = foodCalories;
    }



}
