package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    EditText username, password, repassword;
    Button signUpBtn;
    CheckBox rememberMeBox;
    UserDBHelper db = new UserDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.usernameInputSignup);
        password = findViewById(R.id.passwordInputSignup);
        repassword = findViewById(R.id.repasswordInputSignup);
        rememberMeBox = findViewById(R.id.rememberMeChkBoxSU);
        signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_n = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                boolean rememberMe = rememberMeBox.isChecked();

                // check if passwords match
                if (!pass.equals(repass)) {
                    password.setError("Passwords must match");
                    repassword.setError("Passwords must match");
                    return;
                }

                // add user and sign in, fail if username already exists
                if (db.addUser(user_n, pass, rememberMe)) {
                    Intent signUp = new Intent(context, Home.class);
                    startActivity(signUp);
                } else {
                    username.setError("Username already in use");
                }
            }
        });
    }
}