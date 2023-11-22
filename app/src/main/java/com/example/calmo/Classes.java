package com.example.calmo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Classes extends AppCompatActivity implements RecycleClassViewInterface{

    DataBaseHelper calmoDb;
    ArrayList<ClassModel> classList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        RecyclerView recyclerView = findViewById(R.id.recycleClassView);

        ClassModelViewAdapter adapter = new ClassModelViewAdapter(this, classList,this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calmoDb = new DataBaseHelper(this);
        Cursor cursor = calmoDb.getAllClassData();


        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
                @SuppressLint("Range") String className = cursor.getString(cursor.getColumnIndex("CLASS_NAME"));
                ClassModel data = new ClassModel(id,className);
                classList.add(data);
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Classes.this,ClassProfile.class);
            intent.putExtra("CLASS_NAME", classList.get(position).getClassName());
            intent.putExtra("CLASS_ID", classList.get(position).getClassId());

        startActivity(intent);
    }
}