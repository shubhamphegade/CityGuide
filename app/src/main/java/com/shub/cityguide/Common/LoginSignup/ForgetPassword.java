
package com.shub.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shub.cityguide.R;

public class ForgetPassword extends AppCompatActivity {

    Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

//        Hooks
        nextBtn = findViewById(R.id.forget_password_next_btn);
    }

   public void CallSetNewPassword(View view) {
        startActivity(new Intent(getApplicationContext(), SetNewPassword.class));
    }
}