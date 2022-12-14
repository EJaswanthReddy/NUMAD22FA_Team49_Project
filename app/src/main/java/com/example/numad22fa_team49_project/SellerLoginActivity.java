package com.example.numad22fa_team49_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    FirebaseAuth mAuth;
    TextView sellerSignUp;
    SharedPreferences sharedPreferences;
    ConstraintLayout mainLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        
        loginButton = findViewById(R.id.seller_login_Button);
        
        loginEmail = findViewById(R.id.seller_login_email);
        loginPassword = findViewById(R.id.seller_login_password);
        mAuth = FirebaseAuth.getInstance();
        sellerSignUp = findViewById(R.id.seller_signup);
        sharedPreferences = getSharedPreferences("storeHunt", MODE_PRIVATE);
        mainLayout = findViewById(R.id.for_keyboard_seller);



        
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        sellerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerLoginActivity.this, SellerRegisterationActivity.class));
            }
        });
    }

    private void login() {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            progress.dismiss();
            Toast.makeText(this,"Please enter a valid email",Toast.LENGTH_SHORT).show();
            loginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            progress.dismiss();
            Toast.makeText(this,"Incorrect password",Toast.LENGTH_SHORT).show();
            loginPassword.requestFocus();
        } else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progress.dismiss();
                        Toast.makeText(SellerLoginActivity.this,"User logged in successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SellerLoginActivity.this,SellerProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        SharedPreferences.Editor editSharedPreferences = sharedPreferences.edit();
                        editSharedPreferences.putBoolean("asSeller", true);
                        editSharedPreferences.apply();
                    }else{
                        progress.dismiss();
                        Toast.makeText(SellerLoginActivity.this,"Login error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}