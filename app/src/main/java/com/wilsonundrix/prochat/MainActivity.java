package com.wilsonundrix.prochat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText mainEmail, mainPassword;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(MainActivity.this, Profile.class));
        }

        progressDialog = new ProgressDialog(this);

        mainEmail = (EditText) findViewById(R.id.mainEmail);
        mainPassword = (EditText) findViewById(R.id.mainPassword);

    }

    public void OpenRegistration(View view) {
        startActivity(new Intent(MainActivity.this, SignUp.class));
    }

    public void Login(View view) {
        String user = mainEmail.getText().toString().trim();
        String password = mainPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(user)) {
            if (Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                if (!TextUtils.isEmpty(password)) {
                    //Proceed to sign in the user

                    progressDialog.setMessage("Signing In....");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(user, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                            if(task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(MainActivity.this, Profile.class));
                            }else{
                                Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                   progressDialog.dismiss();

                } else {
                    mainPassword.setError("Please Enter a Password");
                }
            } else {
                mainEmail.setError("Enter a valid E-mail address");
            }
        } else {
            mainEmail.setError("Please Enter an E-mail address");
        }
    }
}
