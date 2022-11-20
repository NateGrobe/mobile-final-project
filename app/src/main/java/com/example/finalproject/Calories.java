package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Calories extends AppCompatActivity {

    wellnessDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        db = new wellnessDB(this,null,null, 1);

        TextView calorieBreakfast = (TextView) findViewById(R.id.calorieBreakfast);
        TextView calorieLunch = (TextView) findViewById(R.id.calorieLunch);
        TextView calorieDinner = (TextView) findViewById(R.id.calorieDinner);
        TextView calorieSnack = (TextView) findViewById(R.id.calorieSnack);
        TextView calorieCount = (TextView) findViewById(R.id.calorieCount);


        int breakfastSave = db.sumCalories(1);
        int lunchSave = db.sumCalories(2);
        int dinnerSave= db.sumCalories(3);
        int snackSave = db.sumCalories(4);

        int calorieTotal = breakfastSave+lunchSave+dinnerSave+snackSave;

        calorieCount.setText(String.valueOf(calorieTotal));
        calorieBreakfast.setText(String.valueOf(breakfastSave));
        calorieLunch.setText(String.valueOf(lunchSave));
        calorieDinner.setText(String.valueOf(dinnerSave));
        calorieSnack.setText(String.valueOf(snackSave));

        Button addMealBtn = (Button) findViewById(R.id.addMeal);

        addMealBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent addMealNav = new Intent(Calories.this, MealSelectionActivity.class);
                startActivity(addMealNav);



            }
        });

    }



}
