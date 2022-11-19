package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Habit extends AppCompatActivity {

    Button add;
    AlertDialog dialog;
    LinearLayout containerLayout, streakLayout, dailyLayout;
    String habitType;
    private RadioGroup radioGroup;
    EditText name;
    Switch sw_activeHabit;

    HabitDBHelper myDb;
    List<HabitModel> habits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        add = findViewById(R.id.add);
        containerLayout = findViewById(R.id.container);
        streakLayout = findViewById(R.id.streak);
        dailyLayout = findViewById(R.id.daily);

        // create the database when the app starts
        myDb = new HabitDBHelper(this,null,null,1);



        buildDialog();

        habits = myDb.getHabits();
        Integer habitCount = habits.size();
//        Toast.makeText(Habit.this, "habit Count " + habitCount, Toast.LENGTH_LONG).show();
        //display the current records in the DB
        displayDatabase();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.habit_dialog, null);

        name = view.findViewById(R.id.nameEdit);
        sw_activeHabit = findViewById(R.id.sw_activeHabit);

        radioGroup = view.findViewById(R.id.radioGroup_habit);
        habitType = "streak";
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radio_streak:
                        habitType = "streak";
                        break;
                    case R.id.radio_daily:
                        habitType = "daily";
                        break;
                }
            }
        });

        builder.setView(view);
        builder.setTitle("Enter Habit")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(name.getText().toString() == null || name.getText().toString().isEmpty() || myDb.CheckIsDataAlreadyExists(name.getText().toString())){
//                             || myDb.CheckIsDataAlreadyExists(name.getText().toString())
                            Toast.makeText(Habit.this, "Error creating habit", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                //create an object from habitModel class
                                HabitModel habitModel = new HabitModel(-1, name.getText().toString(), habitType);
//                                Toast.makeText(Habit.this, habitModel.toString(), Toast.LENGTH_LONG).show();
                                //add new record
                                myDb.addRecord(habitModel);

                                //addCard to layout
                                addCard(name.getText().toString(), habitType);
                            }
                            catch (Exception e) {
                                Toast.makeText(Habit.this, "Error creating habit", Toast.LENGTH_LONG).show();
                            }
                        }

//                        HabitDBHelper habitDBHelper = new HabitDBHelper(Habit.this);




                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialog = builder.create();
    }
    private void addCard(String name, String habitType) {
        View view = getLayoutInflater().inflate(R.layout.habit_card, null);

//        Toast.makeText(Habit.this, name + " " + habitType, Toast.LENGTH_LONG).show();

        TextView nameView = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.delete);


        nameView.setText(name);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(habitType.equals("streak")) {
                    streakLayout.removeView(view);
                }else if(habitType.equals("daily")) {
                    dailyLayout.removeView(view);
                }else {
                    containerLayout.removeView(view);
                }

                //call the deleteRecord method
                myDb.deleteRecord(name);

            }
        });

        if(habitType.equals("streak")) {
            streakLayout.addView(view);
        }else if(habitType.equals("daily")) {
            dailyLayout.addView(view);
        }else {
            containerLayout.addView(view);
        }

    }

    //display the records from DB
    private void displayDatabase() {
//        HabitDBHelper habitDBHelper = new HabitDBHelper();
//        List<HabitModel> habits = myDb.getHabits();

//        ArrayAdapter habitArrayAdapter = new ArrayAdapter<HabitModel>(this, )
        for (int i=0; i<habits.size(); i++) {

//            habits.get(i)
//            Integer habitID = habits.get(i).getId();
            String habitName = habits.get(i).getName();
            String habitType = habits.get(i).getHabitType();
//            Toast.makeText(Habit.this, habits.get(i).getHabitType().toString(), Toast.LENGTH_LONG).show();
            //addCard to layout
            addCard(habitName, habitType);
//            System.out.println(habits.get(i));
        }


//        String temp = myDb.databaseToString();
//        Toast.makeText(Habit.this, habits.toString(), Toast.LENGTH_LONG).show();
//        buckesText =  (TextView) findViewById(R.id.buckesText);
//        buckesText.setText(temp);

    }




}