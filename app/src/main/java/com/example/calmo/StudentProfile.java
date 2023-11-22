package com.example.calmo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentProfile extends AppCompatActivity implements RecycleStudentViewInterface{

    DataBaseHelper calmoDb;
    int studentId;


    public void onCreate(Bundle savedInstanceState) {
        int id;
        String firstName, studentName, contact,strOfId;
        TextView tvTitleName,tvStudentName, tvStudentId,tvStudentContact;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        studentId = getIntent().getIntExtra("ID",0);

        calmoDb = new DataBaseHelper(this);
        StudentModel studentModel = calmoDb.getStudentDetails(studentId);

    //assigning variables with student's data
        id = studentModel.getsId();
        firstName = studentModel.getFirstName();
        contact = studentModel.getContact();

        strOfId = Integer.toString(id);


        studentName = studentModel.getFirstName() + " " + studentModel.getLastName();

    //passing data to the interface
        tvStudentId = findViewById(R.id.studentId);
        tvStudentId.setText(strOfId);

        tvTitleName = findViewById(R.id.titleName);
        tvTitleName.setText(firstName);

        tvStudentName = findViewById(R.id.studentName);
        tvStudentName.setText(studentName);

        tvStudentContact = findViewById(R.id.studentContact);
        tvStudentContact.setText(contact);




//        if (cursor.moveToFirst()) {
//            do {
//                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
//                @SuppressLint("Range") String fName = cursor.getString(cursor.getColumnIndex("FIRST_NAME"));
//                @SuppressLint("Range") String lName = cursor.getString(cursor.getColumnIndex("LAST_NAME"));
//
//                int type = 0;
//                StudentModel data = new StudentModel(id,fName,lName,type);
//
//            }
//            while (cursor.moveToNext());
//        }
   }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public int getId(int position) {
        return 0;
    }
}