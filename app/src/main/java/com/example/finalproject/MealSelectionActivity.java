package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class MealSelectionActivity extends AppCompatActivity{

    EditText userInputFood;
    EditText userInputCalories;
    int calorieTotal;
    TextView buckesText;
    Button backBtn;
    wellnessDB myDb;
    String value = "";
    int mealType;
    Calories cal = new Calories();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_selection);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MealSelectionActivity.this);
        alertDialog.setTitle("Select A Meal");
        String[] items = {"Breakfast", "Lunch", "Dinner", "Snack"};
        int checkedItem = -1;

        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        mealType = 1;
                        createDB();
                        break;
                    case 1:
                        mealType = 2;
                        createDB();
                        break;
                    case 2:
                        mealType = 3;
                        createDB();
                        break;
                    case 3:
                        mealType = 4;
                        createDB();
                        break;
                }
            }

        });
        alertDialog.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

        backBtn = (Button) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealSelectionActivity.this, Calories.class);
                startActivity(i);
            }
        });
    }

    public int countCalories(int calories){
        calorieTotal = calories + calorieTotal;



        return calorieTotal;
    }

    public void createDB(){
        userInputFood = (EditText) findViewById(R.id.userInputFoods);
        userInputCalories = (EditText) findViewById(R.id.userInputCalories);
        buckesText = (TextView) findViewById(R.id.buckesText);
        // create the database when the app starts
        myDb = new wellnessDB(this, null, null, 1);

        displayDatabase();

    }

    //Add new record
    public void addButtonClicked(View v){

        userInputFood = (EditText) findViewById(R.id.userInputFoods);
        userInputCalories = (EditText) findViewById(R.id.userInputCalories);
        //create an object from product class
        Foods food = new Foods (userInputFood.getText().toString(),Double.parseDouble(userInputCalories.getText().toString()));
        calorieTotal = Integer.parseInt(userInputCalories.getText().toString()) + calorieTotal;

        //add new record
        myDb.addRecord(food, mealType);

        //display the whole records
        displayDatabase();
        //clear the user's input
        userInputFood.setText("");
        userInputCalories.setText("");
    }

    //delete records
    public void deleteButtonClicked(View v){
        userInputFood = (EditText) findViewById(R.id.userInputFoods);
        //call the deleteRecord method
        myDb.deleteRecord(userInputFood.getText().toString(), mealType);


        displayDatabase();

        //clear the input text
        userInputFood.setText("");
    }

    //display the records from DB
    private void displayDatabase() {
        String temp = myDb.databaseToString(mealType);
        buckesText =  (TextView) findViewById(R.id.buckesText);
        buckesText.setText(temp);

    }
}