package com.shub.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.shub.cityguide.R;


    public class SignUp3rdClass extends AppCompatActivity {
    Button signUpLoginBtn;
    Button signUpNextButton;
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_class);

        //Hooks
        signUpLoginBtn = findViewById(R.id.signup_login_btn);
        signUpNextButton = findViewById(R.id.signup_next_btn);
        scrollView = findViewById(R.id.signup_3rd_screen_scrool_view);
        countryCodePicker = findViewById(R.id.code_country_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
    }

    public void CallLoginScreen(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    public void callVerifyOTPSignup(View view) {

//        Validation fields
        if(!validatePhoneNumber()){
            return;
        }//validation completed and now move to the next screen to verify phone no and save data

//        Get all values passed from previous screens using Intent
        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");
        String _date = getIntent().getStringExtra("date");
        String _gender = getIntent().getStringExtra("gender");

        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();// Get Phone no
        String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTPSignup.class);

        //Pass all fields to the next activity
        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("phoneNo", _phoneNo);
//        intent.putExtra("whatToDO", "createNewUser"); // This is to identify that which action should OTP perform after verification.

//        startActivity(intent);
//        Add Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkPhoneNo = "^\\d{10}$" ;

        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkPhoneNo)) {
            phoneNumber.setError("Number should be 10 digit !");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}



