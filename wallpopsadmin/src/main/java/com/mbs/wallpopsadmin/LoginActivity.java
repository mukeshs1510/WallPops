package com.mbs.wallpopsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email,password;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmailAdmin);
        password = findViewById(R.id.loginPasswordAdmin);
        loginButton = findViewById(R.id.loginBttnAdmin);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("com.mbs.wallpops_admin_login_status",MODE_PRIVATE);
        String check = sharedPreferences.getString("login_status","off");
        if(check.equals("on")) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else {
            ShowLoginPage();
        }
    }

    private void ShowLoginPage() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_loader);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations =
                        android.R.style.Animation_Dialog;
                dialog.show();

                String EmailAddress = email.getText().toString().trim();
                String Passwordd = password.getText().toString().trim();

                if(EmailAddress.isEmpty() || Passwordd.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content),"Please fill the details!!!",Snackbar.LENGTH_LONG).show();
                } else {
                    mAuth.signInWithEmailAndPassword(EmailAddress,Passwordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                storeAuth();
                                finish();
                                dialog.dismiss();
                            } else {
                                Snackbar.make(findViewById(android.R.id.content),"Login failed!!!",Snackbar.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }

    private void storeAuth() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.mbs.wallpops_admin_login_status", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login_status","on");
        editor.commit();
    }

}