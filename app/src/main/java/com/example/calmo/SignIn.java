package com.example.calmo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {
    private boolean isPassed,isUserNameValid,isPasswordValid;
    private Button signIn,logIn;
    EditText userName,password;
    private String sUserName,sPassword;
    private DataBaseHelper calmoDb;
    private Intent intent1;
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        calmoDb = new DataBaseHelper(this);

        signIn = findViewById(R.id.button8s);
        logIn = findViewById(R.id.button10);
        userName = findViewById(R.id.editText32);
        password = findViewById(R.id.editText);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1 = new Intent(SignIn.this, Login.class);
                startActivity(intent1);
            }
        });

        signIn();

      }

    public void signIn(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isPassed = false;
                // accessing text input
                sUserName = userName.getText().toString();
                sPassword = password.getText().toString();

                //validation of user name and password
                isPasswordValid = validateInput(sPassword);
                isUserNameValid = validateInput(sUserName);

                if (isUserNameValid & isPasswordValid) {
                    isPassed = calmoDb.signIn(sUserName, sPassword);

                    if (isPassed) {
                        intent1 = new Intent(SignIn.this, Login.class);
                        startActivity(intent1);
                    } else {
                        CustomToast.showErrorToast(getApplicationContext(),"Error : Something went wrong",Toast.LENGTH_LONG);
                    }

                } else{
                    CustomToast.showErrorToast(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_LONG);

                }
            }
        });

    }

    public boolean validateInput(String input){
        String inputString = input;
        boolean validity = true;

        if(inputString.equals("")){
            validity = false;
        }
        return validity;
    }

}
