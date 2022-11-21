package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    RelativeLayout calNavBtn, waterNavBtn, habitNavBtn, logOutBtn;
    TextView welcomeTitle, welcomeName, currentCalories, habitCountDisplay;
    ImageView currentWater;
    String user_n;
//    Button calNavBtn, waterNavBtn, habitNavBtn, logOutBtn;
    UserDBHelper db = new UserDBHelper(this);
    WaterDBHelper water_db = new WaterDBHelper(this, null, null, 2);
    wellnessDB calorie_db = new wellnessDB(this, null, null, 1);
    HabitDBHelper habit_db = new HabitDBHelper(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // display welcome to user
        String activeUser = db.getUser();
        welcomeName = findViewById(R.id.welcomeName);
        String usernameStr = "Welcome, " + activeUser + "!";
        welcomeName.setText(usernameStr);

        calNavBtn = findViewById(R.id.calNavBtn);
        waterNavBtn = findViewById(R.id.waterNavBtn);
        habitNavBtn = findViewById(R.id.habitNavBtn);
        logOutBtn = findViewById(R.id.logOutBtn);
        currentWater = findViewById(R.id.currentWater);
        currentCalories = findViewById(R.id.calorieDisplay);
        habitCountDisplay = findViewById(R.id.habitDisplay);

        // display current habit count
        int habitCount = habit_db.getNumberOfHabits();
        String habitStr = habitCount + "\nHabits";
        habitCountDisplay.setText(habitStr);

        // display current calories
        int totalCalories = 0;
        for (int i = 1; i < 5; i++) {
            totalCalories += calorie_db.sumCalories(i);
        }

        String calStr = totalCalories + "\nCals";
        currentCalories.setText(calStr);

        // display current water capacity
        int currentWaterVol = water_db.retrieveVolume();
        String dropletImage = findDropletImage(currentWaterVol);
        int resId = getResources().getIdentifier(dropletImage, "drawable", getPackageName());
        currentWater.setImageResource(resId);


        // basic navigation to the three main screens
        calNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calorieNav = new Intent(context, Calories.class);
                startActivity(calorieNav);
            }
        });

        waterNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent waterNav = new Intent(context, Water.class);
                startActivity(waterNav);
            }
        });

        habitNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent habitNav = new Intent(context, Habit.class);
                startActivity(habitNav);
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.signOut();
                Intent signIn = new Intent(context, MainActivity.class);
                startActivity(signIn);
            }
        });
    }

    public String findDropletImage(int vol) {
        if (vol > 0 && vol < 10) {
            return "waterdroplet" + vol + "bar";
        }
        if (vol > 9) {
            return "waterdropletfull";
        }
        return "waterdropletempty";
    }
}
