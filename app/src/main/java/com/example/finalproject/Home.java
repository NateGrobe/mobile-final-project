package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    RelativeLayout calNavBtn, waterNavBtn, habitNavBtn, logOutBtn;
    TextView welcomeTitle, welcomeName;
    String user_n;
//    Button calNavBtn, waterNavBtn, habitNavBtn, logOutBtn;
    UserDBHelper db = new UserDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String activeUser = db.getUser();
        welcomeTitle = findViewById(R.id.welcomeTitle);
        welcomeName = findViewById(R.id.welcomeName);
        welcomeTitle.setText("Welcome, ");
        welcomeName.setText(activeUser + "!");

        calNavBtn = findViewById(R.id.calNavBtn);
        waterNavBtn = findViewById(R.id.waterNavBtn);
        habitNavBtn = findViewById(R.id.habitNavBtn);
        logOutBtn = findViewById(R.id.logOutBtn);

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
}
