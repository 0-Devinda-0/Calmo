package com.example.calmo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Students extends AppCompatActivity implements RecycleStudentViewInterface {
    DataBaseHelper calmoDb;
    ArrayList<StudentModel> studentList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        calmoDb = new DataBaseHelper(this);
        Cursor cursor = calmoDb.getAllStudentData();

//  ArrayList<Model> dataList = new ArrayList<>();  *** no need

        RecyclerView recyclerView = findViewById(R.id.recycle);
        StudentModelViewAdapter adapter = new StudentModelViewAdapter(this, studentList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (cursor.moveToFirst()) {
            do {

                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("FIRST_NAME"));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("LAST_NAME"));
                @SuppressLint("Range") String contact = cursor.getString(cursor.getColumnIndex("CONTACT_NO"));

                //Conditional selection of Student List Ui component

//                if(paymentStatus.equalsIgnoreCase("done")){
//                    if(!feeType.equalsIgnoreCase("full")){
//                        type = Model.ITEM_TYPE_THREE;
//                        Model data = new Model(id, firstName,lastName, contactNo,eContactNo,feeType,paymentStatus);
//                        dataList.add(data);
//                    }else{
//                        type = Model.ITEM_TYPE_TOW;
//                        Model data = new Model(id, firstName,lastName, contactNo,eContactNo,feeType,paymentStatus);
//                        dataList.add(data);
//                    }
//                }else{
//                    if(!feeType.equalsIgnoreCase("full")){
//                        type = Model.ITEM_TYPE_FOUR;
//                        Model data = new Model(id, firstName,lastName, contactNo,eContactNo,feeType,paymentStatus);
//                        dataList.add(data);
//                    }else{
//                        type = Model.ITEM_TYPE_ONE;
//                        Model data = new Model(id, firstName,lastName, contactNo,eContactNo,feeType,paymentStatus);
//                        dataList.add(data);
//                    }
//                }


                        int type = 1;
                        StudentModel data = new StudentModel(id, firstName,lastName,type,contact);
                        studentList.add(data);
                        Log.i("CLICK_LISTENER_FOR_OUTSIDE", "youClicked white space " + studentList);



            } while (cursor.moveToNext());
        }
//        System.out.println(dataList);
        cursor.close();
        calmoDb.close();


//        CardViewHolder adapter = new CardViewHolder(dataList,this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//
//        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycle);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setAdapter(adapter);

        }

    @Override
    public void onItemClick(int position) {
        Intent intent1 = new Intent(Students.this, StudentProfile.class);
            intent1.putExtra("ID", studentList.get(position).getsId());

        startActivity(intent1);
    }

    @Override
    public int getId(int position) {
        return studentList.get(position).id;
    }
}


