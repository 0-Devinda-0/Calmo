package com.example.calmo;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class ClassProfile extends AppCompatActivity implements RecycleStudentViewInterface {
    DataBaseHelper calmoDb;
    ArrayList<Integer> studentIdList, classPayList;
    Button addToClass, remove;
    ImageButton copyButton;
    EditText studentId;
    StudentModelViewAdapter adapter;
    AlertDialog.Builder confirmAlert;
    int classId;
    ArrayList<StudentModel> studentList = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        String className;
        TextView nameOfClass;

        confirmAlert = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_profile);

        context = this.context;

        //creating a swipe layer out object - for refreshing when swipe down
        swipeRefreshLayout = findViewById(R.id.refresher);

        className = getIntent().getStringExtra("CLASS_NAME");
        classId = getIntent().getIntExtra("CLASS_ID", 0);
        nameOfClass = findViewById(R.id.textView5);

        nameOfClass.setText(className);

        studentId = findViewById(R.id.textInputLayout43);
        addToClass = findViewById(R.id.addToClassButton);
        remove = findViewById(R.id.remove);
        copyButton = findViewById(R.id.copyLink);

        recyclerView = findViewById(R.id.recycleView);

        studentList = dataPrep();

        adapter = new StudentModelViewAdapter(this, studentList, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyLink();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                studentList = dataPrep();
                refresh(context);


            }
        });

//
        addStudentToClass();
        removeStudentFromClass();
    }


    private void refresh(Context con) {
//        studentList = dataPrep();
//        adapter.notifyDataSetChanged();

        adapter = new StudentModelViewAdapter(con, studentList, new RecycleStudentViewInterface() {
            @Override
            public void onItemClick(int position) {
                String fName = studentList.get(position).firstName;
                String lName = studentList.get(position).lastName;
                String fullName = fName + " " + lName;

                confirmAlert
                        .setTitle("Confirm Payment")
                        .setMessage("Confirm the payment of " + fullName)
                        .setCancelable(true)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int currentPosition = getId(position);
                                paymentStatusUpdate(currentPosition);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }

            @Override
            public int getId(int position) {
                return studentList.get(position).id;
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(con));
        adapter.notifyDataSetChanged();
    }

    public void addStudentToClass() {

        addToClass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String studetId = studentId.getText().toString();
                boolean isInserted = false;

                //validating input
                boolean isValid = validateInput(studetId);

                if(isValid) {
                    try {
                        int stId = convertedToInt(studentId);
                        isInserted = calmoDb.addStudentToClass(stId, classId);
                    } catch (Exception e) {
                        CustomToast.showErrorToast(getApplicationContext(), "Student id is not valid", Toast.LENGTH_LONG);
                    }
                }
                if(isInserted) {
                    CustomToast.showDoneToast(getApplicationContext(), "Student added to class successfully",Toast.LENGTH_LONG);
                }else{
                    CustomToast.showErrorToast(getApplicationContext(), "Something went wrong",Toast.LENGTH_LONG);
                }
            }
        });
    }

    public void paymentStatusUpdate(int id) {
        calmoDb = new DataBaseHelper(this);
        boolean isUpdated = calmoDb.updatePaymentValue(id, classId);
        if (isUpdated) {
            CustomToast.showDoneToast(getApplicationContext(), "Payment status updated successfully", Toast.LENGTH_LONG);
        }
        else {
            CustomToast.showErrorToast(getApplicationContext(), "Payment status update failed", Toast.LENGTH_LONG);
        }
    }

    public void removeStudentFromClass() {
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studetId = studentId.getText().toString();

                //validating input
                boolean isValid = validateInput(studetId);
                if(isValid) {
                    try {
                        int stId = convertedToInt(studentId);
                        calmoDb.removeStudentFromClass(stId, classId);
                        CustomToast.showDoneToast(getApplicationContext(), "Student removed from class", Toast.LENGTH_LONG);
                    } catch (Exception e) {
                        CustomToast.showErrorToast(getApplicationContext(), "Student id is not valid", Toast.LENGTH_LONG);

                    }
                }
            }
        });
    }

    public ArrayList<StudentModel> dataPrep() {
        ArrayList<StudentModel> viewList = new ArrayList<>();
        calmoDb = new DataBaseHelper(this);
        Cursor cursor = calmoDb.getAllStudentData();
        studentIdList = calmoDb.getClassStudent(classId);
        classPayList = calmoDb.getClassStudentPayment(classId);
        ArrayList<Integer> returned = new ArrayList<>();
        ArrayList<Integer> returnedPay = new ArrayList<>();
        returnedPay.addAll(classPayList);
        returned.addAll(studentIdList);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("FIRST_NAME"));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("LAST_NAME"));
                @SuppressLint("Range") String contact = cursor.getString(cursor.getColumnIndex("CONTACT_NO"));

                for (int i = 0; i < returned.size(); i++) {
                    if (returned.get(i) == id) {
                        if (returnedPay.get(i) == 1) {
                            int type = 1;

                            StudentModel data = new StudentModel(id, firstName, lastName, type, contact);
                            viewList.add(data);
                        } else {
                            int type = 0;
                            StudentModel data = new StudentModel(id, firstName, lastName, type, contact);
                            viewList.add(data);
                        }
                    }
                }
            }
            while (cursor.moveToNext());
        }
        return viewList;
    }

    @Override
    public void onItemClick(int position) {

        String fName = studentList.get(position).firstName;
        String lName = studentList.get(position).lastName;
        String fullName = fName + " " + lName;

        confirmAlert
                .setTitle("Confirm Payment")
                .setMessage("Confirm the payment of " + fullName)
                .setCancelable(true)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int currentPosition = getId(position);
                        paymentStatusUpdate(currentPosition);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();

    }
    public void copyLink(){
        String link = calmoDb.getClassLocation(classId);
        if(link.equals("n/a")){
            CustomToast.showErrorToast(getApplicationContext(),"Location details not available",Toast.LENGTH_LONG);
        }else{
            // Get the clipboard system service
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", link);
            clipboard.setPrimaryClip(clip);
            CustomToast.showInfoToast(getApplicationContext(),"Text copied to clipboard",Toast.LENGTH_LONG);
        }

    }

    @Override
    public int getId(int position) {
        return studentList.get(position).id;
    }

    public int convertedToInt(EditText text) {
        String thisText = text.getText().toString();
        int intValue = Integer.parseInt(thisText);
        return intValue;
    }

    public boolean validateInput(String input){
        return !input.equals("");
    }

}


