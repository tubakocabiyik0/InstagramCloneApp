package com.example.instagramcloneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagramcloneapp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Logon extends AppCompatActivity {
    EditText username;
    EditText password1;
    EditText password2;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        username = findViewById(R.id.editTextTextPersonName2);
        password1 = findViewById(R.id.editTextTextPersonName3);
        password2 = findViewById(R.id.editTextTextPersonName4);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void logon(View v) {
        if(password1.getText().toString().equals(password2.getText().toString())) {
            firebaseAuth.createUserWithEmailAndPassword(username.getText().toString(), password1.getText().toString()).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    HomeFragment homeFragment=new HomeFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.logon,homeFragment).commit();

                }
            });


        }else{
            Toast.makeText(this,"second password is wrong",Toast.LENGTH_LONG).show();
        }
    }
}