package com.wilsonundrix.prochat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Profile extends AppCompatActivity {

    TextView userName,userEmail,userPhone;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(Profile.this, MainActivity.class));
        }

        userEmail = (TextView) findViewById(R.id.tvUserEmail);
        userName = (TextView) findViewById(R.id.tvUserName);
        userPhone = (TextView) findViewById(R.id.tvUserPhone);


        FirebaseUser user = firebaseAuth.getCurrentUser();
        userEmail.setText(user.getEmail());


    }

    public void LogOut(View view) {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Profile.this, MainActivity.class));
    }
}
