package com.example.calmo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class CreateClass extends AppCompatActivity {
    private Button addLocation, addClass;
    EditText className;
    String startDate, feePlan;
    DataBaseHelper calmoDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        calmoDb = new DataBaseHelper(this);

        //buttons
        addClass = findViewById(R.id.button6);
        addLocation = findViewById(R.id.button4);

        //text fields
        className = findViewById(R.id.className);
        //tDate = "yesterday";
        //feePlan = "nothing";

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateClass.this, MapFragment.class);
                intent.putExtra("CLASS_NAME", className.getText().toString());
                //intent.putExtra("CLASS_ID", classList.get(position).getClassId());
                startActivity(intent);

            }
        });
        addClass();

    }

    public void addClass() {
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notAwail = "n/a";
                boolean isInserted = calmoDb.addClass(className.getText().toString(),notAwail,className.getText().toString());

                if(isInserted)
                    CustomToast.showDoneToast(getApplicationContext(),"Class created successfully ", Toast.LENGTH_LONG);
                else
                    CustomToast.showErrorToast(getApplicationContext(),"Class not created ", Toast.LENGTH_LONG);

            }
        });
    }
}