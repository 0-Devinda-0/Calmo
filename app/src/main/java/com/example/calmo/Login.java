package com.example.calmo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class Login extends AppCompatActivity{

    private Button login;
    private boolean isUserNameValid,isPasswordValid;
    private EditText userName,password;
    private String sUserName,sPassword;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private DataBaseHelper calmoDb;
    private Intent intent1;


        protected void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            login = findViewById(R.id.button12);
            calmoDb = new DataBaseHelper(this);

            Button biometricLogin = findViewById(R.id.biometric_login_button);
            userName = findViewById(R.id.user_name_in_login);
            password = findViewById(R.id.password_in_login);


//            ******************************************************Delete form here
//            login.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent1 = new Intent(Login.this, Dash.class);
//                    startActivity(intent1);
//
//
//
//                      adb -e emu finger touch <finger_id>
//
//
//                }
//            });
//          ***********************************************

//              Biometric Security implementation(fingerprint)

            Executor executor = ContextCompat.getMainExecutor(this);
                    biometricPrompt = new BiometricPrompt(Login.this,
                            executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    CustomToast.showErrorToast(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT);
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    CustomToast.showDoneToast(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(Login.this, Dash.class);
                    startActivity(intent);

                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    CustomToast.showErrorToast(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT);
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Touch Fingerprint Sensor")
                    .setSubtitle("Log in using your biometric credential")
                    .setNegativeButtonText("Use account password")
                    .build();

            biometricLogin.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));

            normalLogin();
        }

    // method for login in with password and user name
    private void normalLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isValidUser = false;

                sUserName = userName.getText().toString();
                sPassword = password.getText().toString();

                //validation of user name and password
                isPasswordValid = validateInput(sPassword);
                isUserNameValid = validateInput(sUserName);

                if (isUserNameValid & isPasswordValid) {
                    try {
                        isValidUser = calmoDb.login(sUserName, sPassword);
                    } catch (Exception e) {
                        CustomToast.showErrorToast(getApplicationContext(),"Check user name and password",Toast.LENGTH_LONG);

                    }

                    if (isValidUser) {
                        intent1 = new Intent(Login.this, Dash.class);
                        CustomToast.showDoneToast(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT);
                        startActivity(intent1);
                    } else {
                        CustomToast.showErrorToast(getApplicationContext(), "Check your user name and password",Toast.LENGTH_LONG);
              }

                } else {
                    CustomToast.showErrorToast(getApplicationContext(), "Fields cannot be empty",Toast.LENGTH_LONG);
                }
            }
        });

    }

    // text field validation
    public boolean validateInput(String input){
        return !input.equals("");
    }
}


