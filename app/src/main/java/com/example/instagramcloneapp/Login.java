package com.example.instagramcloneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText userName;
    EditText password;
    Button login;
    Button logon;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
    }

    public void login(View v) {
        firebaseAuth.signInWithEmailAndPassword(userName.getText().toString(),password.getText().toString());
    }

    public void logon(View v) {
        Intent i = new Intent(getApplicationContext(),Logon.class);
        startActivity(i);
        finish();
    }


}