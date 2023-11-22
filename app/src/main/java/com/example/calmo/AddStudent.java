package com.example.calmo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudent extends AppCompatActivity {
    private RadioGroup radioGroup;
    private Button addStudentButton;
    EditText firstName,lastName,contactNo,eContactNo;
    DataBaseHelper calmoDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        calmoDb = new DataBaseHelper(this);

//        radio buttons
//        radioGroup = findViewById(R.id.radioButtonGroup);

//        buttons
        addStudentButton = findViewById(R.id.addStudent);

//        text fields
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        contactNo = findViewById(R.id.contactNo);
        eContactNo = findViewById(R.id.eContactNo);

        addStudent();
    }
    public void addStudent() {

        addStudentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//              getting radio button values
//              int selectedRadioButton = radioGroup.getCheckedRadioButtonId();
                String paymentStatus = "nd";
//              RadioButton radioButton = findViewById(selectedRadioButton);

                boolean isInserted = calmoDb.addStudent(firstName.getText().toString(),lastName.getText().toString(),contactNo.getText().toString(),eContactNo.getText().toString(),paymentStatus);

                if(isInserted)
//                 Toast.makeText(AddStudent.this,"Student added successfully", Toast.LENGTH_LONG).show();
                    CustomToast.showDoneToast(getApplicationContext(), "Student added successfully",Toast.LENGTH_LONG);
                else
                    CustomToast.showErrorToast(getApplicationContext(), "Something went wrong",Toast.LENGTH_LONG);
            }

        });

    }

}