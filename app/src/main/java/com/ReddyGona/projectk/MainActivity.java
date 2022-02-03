package com.ReddyGona.projectk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    EditText mobile_ed;
    LinearLayout otp_layout;
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
    Button otp_btn;

    CardView google_signin;

    String otp_s, verification_code;
    ProgressDialog progressDialog;

    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mobile_ed=findViewById(R.id.ed_mobile);
        otp_layout=findViewById(R.id.otp_layout);

        ed1 = findViewById(R.id.ed_1);
        ed2 = findViewById(R.id.ed_2);
        ed3 = findViewById(R.id.ed_3);
        ed4 = findViewById(R.id.ed_4);
        ed5 = findViewById(R.id.ed_5);
        ed6 = findViewById(R.id.ed_6);

        google_signin=findViewById(R.id.cardView2);

        initialize();

        google_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });

        otp_btn = findViewById(R.id.otp_btn);

        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp_btn.getText().toString().equals("request otp")){
                    mobile_login();
                }
                if(otp_btn.getText().toString().equals("verify otp")){
                    fill_otp();
                }
            }
        });


        request_permission();

        //initialising firebase auth
        firebaseAuth= FirebaseAuth.getInstance();

    }

    private void initialize() {
        //signing options
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("570278501367-1taud0mh0lpsk6n7d0s0t4ob74ngur48.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //signing clint
        googleSignInClient = GoogleSignIn.getClient(MainActivity.this,
                gso);
    }

    private void fill_otp() {
        movetonext();
        if (ed1.getText().toString().trim().isEmpty() ||
                ed2.getText().toString().trim().isEmpty() ||
                ed3.getText().toString().trim().isEmpty() ||
                ed4.getText().toString().trim().isEmpty() ||
                ed5.getText().toString().trim().isEmpty() ||
                ed6.getText().toString().trim().isEmpty() ){
            Toast.makeText(MainActivity.this, "Please Enter Valid Otp", Toast.LENGTH_SHORT).show();
        }else{
            otp_s = ed1.getText().toString() +
                    ed2.getText().toString() +
                    ed3.getText().toString() +
                    ed4.getText().toString() +
                    ed5.getText().toString() +
                    ed6.getText().toString();

            verifyOTP(verification_code);
        }
    }

    private void movetonext() {
        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (ed1.getText().toString().length()==1){
                        ed2.requestFocus();
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ed2.getText().toString().length()==1){
                    ed3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ed3.getText().toString().length()==1){
                    ed4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ed4.getText().toString().length()==1){
                    ed5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ed5.getText().toString().length()==1){
                    ed6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void mobile_login() {
        if (mobile_ed.getText().toString().trim().isEmpty()){
            Toast.makeText(MainActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
        }else{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait.. Requesting OTP");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + mobile_ed.getText().toString(),
                    30,
                    TimeUnit.SECONDS,
                    MainActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            progressDialog.dismiss();
                            otp_layout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            progressDialog.dismiss();
                            otp_layout.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Failed to send OTP "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String Verified, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            otp_layout.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                            verification_code=Verified;
                            otp_btn.setText("verify otp");
                        }
                    }
            );
        }
    }

    private void verifyOTP(String verified) {
        if(verified!=null){
            progressDialog.setMessage("verifying OTP");
            progressDialog.show();
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                    verified,
                    otp_s
            );
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                Intent intent= new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("profile", "1");
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()){
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    if(googleSignInAccount!=null){
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Intent intent= new Intent(MainActivity.this, HomeActivity.class);
                                            intent.putExtra("profile", "1");
                                            startActivity(intent);
                                        }
                                    }
                                });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void request_permission() {
        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this, permission);

        if(grant!= PackageManager.PERMISSION_GRANTED){
            String[] permission_list = new String[1];
            permission_list[0] = permission;

            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }
}