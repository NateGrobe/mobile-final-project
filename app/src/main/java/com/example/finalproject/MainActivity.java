package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button calNavBtn, waterNavBtn, habitNavBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calNavBtn = findViewById(R.id.calNavBtn);
        waterNavBtn = findViewById(R.id.waterNavBtn);
        habitNavBtn = findViewById(R.id.habitNavBtn);

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
    }
}