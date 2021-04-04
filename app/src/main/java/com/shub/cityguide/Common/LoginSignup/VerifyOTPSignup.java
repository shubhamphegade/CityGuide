package com.shub.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shub.cityguide.Databases.UserHelperClass;
import com.shub.cityguide.R;

import java.util.concurrent.TimeUnit;

public class VerifyOTPSignup extends AppCompatActivity {

    Button verifyotp;
    PinView pinFromUser;
    String codeBySystem;
    TextView otpDescriptionText;
    String fullName, phoneNo, email, username, password, date, gender, whatToDO;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_o_t_p_signup);
        verifyotp = findViewById(R.id.verify_code_btn);

        //hooks
        pinFromUser = findViewById(R.id.pin_view);
        otpDescriptionText = findViewById(R.id.otp_description_text);

        //Get all the data from Intent
        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");
        phoneNo = getIntent().getStringExtra("phoneNo");

       Toast.makeText(VerifyOTPSignup.this,fullName,Toast.LENGTH_LONG).show();

       sendVerificationCodeToUser(phoneNo);
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,// Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;

                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(VerifyOTPSignup.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(VerifyOTPSignup.this, "Verification Completed", Toast.LENGTH_SHORT).show();
                            storeNewUsersData();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTPSignup.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void storeNewUsersData() {
        Toast.makeText(VerifyOTPSignup.this, "reached", Toast.LENGTH_SHORT).show();
        try {
              rootNode  = FirebaseDatabase.getInstance();
              reference = rootNode.getReference("Users");
        }
        catch (Exception e){
            Toast.makeText(VerifyOTPSignup.this, "something went wrong", Toast.LENGTH_SHORT).show();
        }

//        UserHelperClass addNewUser =  new UserHelperClass(fullName, username, email, phoneNo, password, date, gender);
//        reference.child(phoneNo).setValue(addNewUser);

//        reference.setValue("First data");
    }


    public void callNextScreenFromOTP(View view) {
        String code  = pinFromUser.getText().toString();
        if (!code.isEmpty()){
            verifyCode(code);
        }
    }
}