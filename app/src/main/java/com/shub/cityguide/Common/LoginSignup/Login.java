package com.shub.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shub.cityguide.R;
import com.shub.cityguide.User.AllCategories;

public class Login extends AppCompatActivity {

    Button forgetPasswordBtn, createAccountbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);

        forgetPasswordBtn = findViewById(R.id.login_forget_password);
        createAccountbtn = findViewById(R.id.login_create_acc_btn);
    }

    public void callMakeSelectionScreen(View view){
        startActivity(new Intent(getApplicationContext(), MakeSelection.class));
    }

    public void callCreateAccountScreen(View view) {
        startActivity(new Intent(getApplicationContext(), SignUp.class));
    }
}