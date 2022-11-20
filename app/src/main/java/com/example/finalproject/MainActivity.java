package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button signInBtn, signUpBtn;
    CheckBox rememberMeBox;
    UserDBHelper db = new UserDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent homeNav = new Intent(context, Home.class);

        if (db.rememberSignIn()) {
            startActivity(homeNav);
        }

        username = findViewById(R.id.usernameInputLogin);
        password = findViewById(R.id.passwordInputLogin);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpNavBtn);
        rememberMeBox = findViewById(R.id.rememberMeChkBoxSI);

        // navigate to home screen
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_n = username.getText().toString();
                String pass = password.getText().toString();
                boolean rememberMe = rememberMeBox.isChecked();

                if (db.signInUser(user_n, pass, rememberMe)) {
                    username.setError(null);
                    password.setError(null);
                    startActivity(homeNav);
                } else {
                    username.setError("Incorrect username or password");
                    password.setError("Incorrect username or password");
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpNav = new Intent(context, SignUp.class);
                startActivity(signUpNav);
            }
        });
    }
}