package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Habit extends AppCompatActivity {

    Button add;
    AlertDialog dialog;
    LinearLayout containerLayout, streakLayout, dailyLayout;
    String habitType;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        add = findViewById(R.id.add);
        containerLayout = findViewById(R.id.container);
        streakLayout = findViewById(R.id.streak);
        dailyLayout = findViewById(R.id.daily);

        buildDialog();



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

        EditText name = view.findViewById(R.id.nameEdit);

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
                        addCard(name.getText().toString(), habitType);
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

        TextView nameView = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.delete);


        nameView.setText(name);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(habitType == "streak") {
                    streakLayout.removeView(view);
                }else if(habitType == "daily") {
                    dailyLayout.removeView(view);
                }else {
                    containerLayout.removeView(view);
                }

            }
        });

        if(habitType == "streak") {
            streakLayout.addView(view);
        }else if(habitType == "daily") {
            dailyLayout.addView(view);
        }else {
            containerLayout.addView(view);
        }

    }


}