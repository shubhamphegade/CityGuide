package com.shub.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shub.cityguide.R;

public class VerifyOTP extends AppCompatActivity {

    Button verifyOTPBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

//        Hooks
        verifyOTPBtn = findViewById(R.id.verify_code_btn);
    }

    public void callSetNewPassword(View view) {
        startActivity(new Intent(getApplicationContext(), SetNewPassword.class));
    }
}