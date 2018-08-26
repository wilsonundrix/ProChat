package com.wilsonundrix.prochat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class SignUp extends AppCompatActivity {

    EditText signUpEmail, signUpPassword, signUpPasswordConfirm,signUpUsername,signUpPhone;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpEmail = (EditText) findViewById(R.id.signUpEmail);
        signUpPassword = (EditText) findViewById(R.id.signUpPassword);
        signUpPasswordConfirm = (EditText) findViewById(R.id.signUpPasswordConfirm);
        signUpUsername = (EditText) findViewById(R.id.signUpUsername);
        signUpPhone = (EditText) findViewById(R.id.signUpPhone);

        progressDialog = new ProgressDialog(this);

    }

    public void RegisterUser(View view) {

        String username = signUpUsername.getText().toString().trim();
        String email = signUpEmail.getText().toString().trim();
        String phone = signUpPhone.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();
        String passwordConfirm = signUpPasswordConfirm.getText().toString().trim();

        if (!TextUtils.isEmpty(email)) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!TextUtils.isEmpty(password)) {
                    if (!TextUtils.isEmpty(passwordConfirm)) {
                        if (password.equals(passwordConfirm)) {
                            if (!TextUtils.isEmpty(username)) {
                            //Proceed to register the user

                            progressDialog.setMessage("Registering User....");
                            progressDialog.show();

                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Registration successful & open Profile Page
                                        finish();
                                        startActivity(new Intent(SignUp.this, Profile.class));
                                    }else{
                                        Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            progressDialog.dismiss();
                            } else {
                                signUpUsername.setError("Please Enter a username");
                            }
                        } else {
                            signUpPasswordConfirm.setError("Please Confirm the Password");
                        }
                    } else {
                        signUpPasswordConfirm.setError("Please Confirm the Password");
                    }
                } else {
                    signUpPassword.setError("Please Enter a Password");
                }
            } else {
                signUpEmail.setError("Enter a valid E-mail address");
            }
        } else {
            signUpEmail.setError("Please Enter an E-mail address");
        }

    }

    public void OpenLogin(View view) {
        startActivity(new Intent(SignUp.this, MainActivity.class));
    }
}
