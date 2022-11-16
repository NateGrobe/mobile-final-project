package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Calories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        Button addMealBtn = (Button) findViewById(R.id.addMeal);

        addMealBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Calories.this);
                alertDialog.setTitle("Select A Meal");
                String[] items = {"Breakfast", "Lunch", "Dinner", "Snack"};
                int checkedItem = 1;
                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(Calories.this, "Breakfast", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                Toast.makeText(Calories.this, "Lunch", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Toast.makeText(Calories.this, "Dinner", Toast.LENGTH_LONG).show();
                                break;
                            case 3:
                                Toast.makeText(Calories.this, "Snack", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                });
                alertDialog.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent addMealNav = new Intent(Calories.this, MealSelectionActivity.class);
                        startActivity(addMealNav);
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();




            }
        });

    }
}
