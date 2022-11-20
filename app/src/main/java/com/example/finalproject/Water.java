package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;

public class Water extends AppCompatActivity {
    WaterDBHelper DB;
    EditText amountText;
    Spinner unitSpinner, outputUnitSpinner;
    Button addBtn, subBtn;
    ImageView image;
    TextView totalCups;
    String unit;
    int volume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        amountText = findViewById(R.id.amountText);
        unitSpinner = findViewById(R.id.unitSpinner);
        outputUnitSpinner = findViewById(R.id.outputUnitSpinner);
        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        image=findViewById(R.id.image);
        totalCups= findViewById(R.id.totalCups);

        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);
        outputUnitSpinner.setAdapter(adapter);

        DB = new WaterDBHelper(this, null, null, 2);
        volume = 0;
        unit = "Cups";
        amountText.setText("0");
        DB.addRecord(volume, unit);
        DB.deleteRecords();
        int currentVolume = DB.retrieveVolume();
        totalCups.setText(String.valueOf(currentVolume));
        setImage(currentVolume);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(amountText.getText())) {
                    amountText.setError("Volume Required");
                }
                else{
                    volume = Integer.parseInt(amountText.getText().toString());
                    unit = unitSpinner.getSelectedItem().toString();
                    String outputUnit = outputUnitSpinner.getSelectedItem().toString();
                    if(unit.equals("ML")){
                        volume = volume/250;
                        unit = "Cups";
                    }
                    addTotal(DB, volume, unit, outputUnit);
                }
                    amountText.setText("0");
            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(amountText.getText())) {
                    amountText.setError("Volume Required");

                }
                else{
                    volume = Integer.parseInt(amountText.getText().toString());
                    unit = unitSpinner.getSelectedItem().toString();
                    String outputUnit = outputUnitSpinner.getSelectedItem().toString();
                    if(unit.equals("ML")){
                        volume = volume/250;
                        unit = "Cups";
                    }
                    subTotal(DB, volume, unit, outputUnit);
                }
                amountText.setText("0");

            }
        });



        outputUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String unitSelected = outputUnitSpinner.getItemAtPosition(position).toString();
                Toast.makeText(Water.this, "you selected: " + unitSelected,Toast.LENGTH_LONG).show();
                changeMeasurements(unitSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void changeMeasurements(String unitSelected) {
        int currentVol = Integer.parseInt(totalCups.getText().toString());
        if(unitSelected.equals("ML")) {
            currentVol = currentVol * 250;
        }else{
            currentVol = currentVol / 250;
        }
        totalCups.setText(String.valueOf(currentVol));
    }

    private void addTotal(WaterDBHelper DB, int currentVolume, String currentUnit, String SelectedUnit){

        int oldVolume = DB.retrieveVolume();
        Toast.makeText(this, String.valueOf(oldVolume), Toast.LENGTH_LONG).show();
        int newVolume = oldVolume + currentVolume;
        setImage(newVolume);
        DB.updateRecord(newVolume, currentUnit);
        if (SelectedUnit.equals("ML")) {
            newVolume = newVolume * 250;
        }
        totalCups.setText(String.valueOf(newVolume));

    }
    private void subTotal(WaterDBHelper DB, int currentVolume, String currentUnit, String SelectedUnit){
        int newVolume = 0;
        int oldVolume = DB.retrieveVolume();
        Toast.makeText(this, String.valueOf(oldVolume), Toast.LENGTH_LONG).show();
        if(oldVolume >= currentVolume) {
           newVolume = oldVolume - currentVolume;
        }
        DB.updateRecord(newVolume, currentUnit);
        setImage(newVolume);
        if (SelectedUnit.equals("ML")) {
            newVolume = newVolume * 250;
        }
        totalCups.setText(String.valueOf(newVolume));
    }
    public void setImage(int volume){
        Toast.makeText(this, "Volume: " + volume, Toast.LENGTH_LONG).show();
        switch (volume){
            case 0:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdropletempty));
                break;
            case 1:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdroplet1bar));
                break;
            case 2:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdroplet2bar));
                break;
            case 3:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdroplet3bar));
                break;
            case 4:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdroplet4bar));
                break;
            case 5:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdroplet5bar));
                break;
            case 6:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdroplet6bar));
                break;
            case 7:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdroplet7bar));
                break;
            case 8:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdroplet8bar));
                break;
            case 9:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdroplet9bar));
                break;
            default:
                image.setImageDrawable(getResources().getDrawable(R.drawable.waterdropletfull));
                break;
        }
    }
}