package com.example.calmo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import java.util.concurrent.Executor;


public class Dash extends AppCompatActivity{
    private Button addStudent,createClass,studentList,tempButton;
    private Executor executor;
    private TextView studentCount,classCount;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    DataBaseHelper calmoDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int stdCount,clsCount;
        super.onCreate(savedInstanceState);
        calmoDb = new DataBaseHelper(this);
        setContentView(R.layout.activity_dash);
        addStudent = findViewById(R.id.addStudentButton);
        createClass = findViewById(R.id.button5);
        studentList = findViewById(R.id.button9);
        tempButton = findViewById(R.id.button3);

        // displaying counts of classes and students
        try {
            stdCount = calmoDb.recordCount("students_table");
            clsCount = calmoDb.recordCount("class_table");

        } catch (Exception e) {
            stdCount = 0;
            clsCount = 0;
        }
        studentCount = findViewById(R.id.studentCount);
        classCount = findViewById(R.id.classCount);
        studentCount.setText(Integer.toString(stdCount));
        classCount.setText(Integer.toString(clsCount));

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dash.this, AddStudent.class);
                startActivity(intent);

            }
        });
        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dash.this, CreateClass.class);
                startActivity(intent1);

            }
        });
        studentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dash.this, Students.class);
                startActivity(intent1);

            }
        });
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dash.this, Classes.class);
                startActivity(intent1);

            }
        });

    }


}