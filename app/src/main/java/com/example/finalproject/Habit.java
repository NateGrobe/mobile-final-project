package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Habit extends AppCompatActivity {

    Button add;
    AlertDialog dialog, calendarDialog;
    LinearLayout containerLayout, streakLayout, dailyLayout;
    String habitType;
    private RadioGroup radioGroup;
    ListView presetHabitList;
    EditText name;
    TextView Edit_startDate, Edit_missedDays, Edit_totalDays;
    Switch sw_activeHabit;

    HabitDBHelper myDb;
    List<HabitModel> habits;

    int missedDays, totalDays;

    ArrayList<String> presetHabits = new ArrayList<>();

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


        get_json();
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
                name.setText("");
            }

        });




    }

    public void get_json() {
        String json;
        try {
            InputStream is = getResources().openRawResource(R.raw.preset_habits);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);


            for (int i = 0; i < jsonArray.length(); i++) {
//                Toast.makeText(getApplicationContext(), "now here " + i, Toast.LENGTH_LONG).show();
                JSONObject obj = jsonArray.getJSONObject(i);
                presetHabits.add(obj.getString("name"));
//                presetHabits.add("here");

//                if(obj.getString(""))
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void openCalendarDialog(HabitModel habitModel) {
//        habitModel.getName(), habitModel.getHabitType(), habitModel.getStartDate(), habitModel.getMissedDays(), habitModel.getTotalDays();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.habit_calendar_dialog, null);


        Edit_startDate = view.findViewById(R.id.Edit_startDate);
        Edit_missedDays = view.findViewById(R.id.Edit_missedDays);
        Edit_totalDays = view.findViewById(R.id.Edit_totalDays);

        String startDate = habitModel.getStartDate();
        missedDays = habitModel.getMissedDays();



        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy");
//        System.out.println ("df: " + df);

        String currentDate = df.format(c);
//        System.out.println ("currentDate: " + currentDate);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate date1 = LocalDate.parse(startDate, dtf);
        LocalDate date2 = LocalDate.parse(currentDate, dtf);
        Period daysBetween = Period.between(date1, date2);
//        System.out.println ("Days: " + daysBetween.getDays());

        Integer total = daysBetween.getDays() - missedDays;

        Edit_startDate.setText(startDate);
        Edit_missedDays.setText(String.valueOf(missedDays));
        Edit_totalDays.setText(total.toString());

        TextView plusMissed = view.findViewById(R.id.plusMissed);
        TextView minusMissed = view.findViewById(R.id.minusMissed);

        plusMissed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String missedStr = Edit_missedDays.getText().toString();
                int missedInt = Integer.parseInt(missedStr);
                missedInt++;
                Edit_missedDays.setText(String.valueOf(missedInt));
                habitModel.setMissedDays(missedInt);
                Integer total = daysBetween.getDays() - missedDays;
                habitModel.setTotalDays(total);
            }
        });

        minusMissed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String missedStr = Edit_missedDays.getText().toString();
                int missedInt = Integer.parseInt(missedStr);
                if(missedInt != 0){
                    missedInt--;
                    Edit_missedDays.setText(String.valueOf(missedInt));
                    habitModel.setMissedDays(missedInt);
                    Integer total = daysBetween.getDays() - missedDays;
                    habitModel.setTotalDays(total);
                }


            }
        });


        builder.setView(view);
        builder.setTitle("Calendar")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        myDb.updateRecord(habitModel);
                    }
                });
        calendarDialog = builder.create();
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.habit_dialog, null);

        name = view.findViewById(R.id.nameEdit);
//        sw_activeHabit = findViewById(R.id.sw_activeHabit);

        radioGroup = view.findViewById(R.id.radioGroup_habit);
        habitType = "streak";

        presetHabitList = view.findViewById(R.id.presetHabitList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.habit_preset_list,
                R.id.list_preset_content,
                presetHabits
        );
        presetHabitList.setAdapter(arrayAdapter);

        presetHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(Habit.this, "clicked on " + presetHabits.get(i), Toast.LENGTH_LONG).show();
                name.setText(presetHabits.get(i));
            }
        });



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
        builder.setTitle("Add Habit")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(name.getText().toString() == null || name.getText().toString().isEmpty() || myDb.CheckIsDataAlreadyExists(name.getText().toString())){
//                             || myDb.CheckIsDataAlreadyExists(name.getText().toString())
                            Toast.makeText(Habit.this, "Error creating habit", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                //get date
                                Date c = Calendar.getInstance().getTime();


                                SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
                                String startDate = df.format(c);
//                                System.out.println("time: Current time => " + startDate);

                                missedDays = 0;
                                totalDays = 0;

                                //create an object from habitModel class
                                HabitModel habitModel = new HabitModel(-1, name.getText().toString(), habitType, startDate, missedDays, totalDays);
//                                Toast.makeText(Habit.this, startDate, Toast.LENGTH_LONG).show();
                                //add new record
                                myDb.addRecord(habitModel);

                                //addCard to layout
                                addCard(habitModel);
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
    private void addCard(HabitModel habitModel) {
        View view = getLayoutInflater().inflate(R.layout.habit_card, null);

        habitType = habitModel.getHabitType();
        System.out.println("habitType => " + habitType);

//        Toast.makeText(Habit.this, name + " " + habitType, Toast.LENGTH_LONG).show();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCalendarDialog(habitModel);
                calendarDialog.show();
//                name.setText("");
            }

        });


        TextView nameView = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.delete);
        TextView startDate = view.findViewById(R.id.startDate);

        startDate.setText(habitModel.getStartDate());
        nameView.setText(habitModel.getName());


        delete.setOnClickListener(new View.OnClickListener() {
            String deleteHabitType = habitType;
            @Override
            public void onClick(View v) {
                System.out.println("delete.setOnClickListener => " + v);
                if(deleteHabitType.equals("streak")) {
                    System.out.println("habitType.equals(\"streak\") => " + v);
                    streakLayout.removeView(view);
                    //call the deleteRecord method
                    myDb.deleteRecord(habitModel.getName());
                }else if(deleteHabitType.equals("daily")) {
                    System.out.println("habitType.equals(\"daily\") => " + v);
                    dailyLayout.removeView(view);
                    //call the deleteRecord method
                    myDb.deleteRecord(habitModel.getName());
                }else {
                    containerLayout.removeView(view);
                }



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

        for (int i=0; i<habits.size(); i++) {

//            habits.get(i)
//            Integer habitID = habits.get(i).getId();
            String habitName = habits.get(i).getName();
            String habitType = habits.get(i).getHabitType();
            String habitStartDate = habits.get(i).getStartDate();
            int habitMissedDays = habits.get(i).getMissedDays();
            int habitTotalDays = habits.get(i).getTotalDays();

            HabitModel habitModel = new HabitModel(-1, habitName, habitType, habitStartDate, habitMissedDays, habitTotalDays);

            //get date
//            System.out.println("time: habitDate => " + habitStartDate);


            //addCard to layout
            addCard(habitModel);
            System.out.println(habits.get(i));
        }


    }




}