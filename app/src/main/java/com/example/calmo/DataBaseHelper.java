package com.example.calmo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String  DATABASE_NAME = "Calmo.db";
    public static final String  STUDENTS_TABLE = "students_table";
    public static final String  CLASS_TABLE= "class_table";
    public static final String USERS = "users";


    public static final String  USER_NAME = "user_name";
    public static final String  PASSWORD = "password";
    public static final String  STUDENT_ID = "student_id";
    public static final String  FIRST_NAME= "first_name";
    public static final String  LAST_NAME= "last_name";
    public static final String  STUDENT_CONTACT_NO = "contact_no";
    public static final String  EMERGENCY_CONTACT_NO = "e_contact_no";
    public static final String  CLASS_ID= "class_id";
    public static final String  CLASS_NAME = "class_name";
    public static final String  LOCATION_LINK = "location_link";
   // public static final String  FEE_PLAN= "fee_plan";


    //Fees_Table
    public static final String FEES_TABLE = "fees_table";
    public static final String STUDENT_FEE_ID = "student_fee_id";
    public static final String CLASS_FEE_ID = "class_fee_id";
    public static final String LAST_UPDATED = "last_updated";
    public static final String  PAYMENT_STATUS = "payment_status";
    public static final String  FEE_TYPE  = "fee_type";
    int studentId;
    int classId;
    String feeType;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" create table " + STUDENTS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "FIRST_NAME TEXT," + "LAST_NAME TEXT," + "CONTACT_NO TEXT," + "E_CONTACT_NO TEXT," +"PAYMENT_STATUS TEXT )");
        db.execSQL(" create table " + CLASS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "CLASS_NAME TEXT," + " LOCATION_LINK TEXT ) ");
        db.execSQL(" create table " + FEES_TABLE + "(STUDENT_FEE_ID INTEGER,"+ "CLASS_FEE_ID INTEGER," + " LAST_UPDATED TEXT," + "FEE_TYPE TEXT," + " PAYMENT_STATUS TEXT,"+ "FOREIGN KEY (STUDENT_FEE_ID) REFERENCES STUDENT_TABLE(ID), " + "FOREIGN KEY (CLASS_FEE_ID) REFERENCES CLASS_TABLE(ID), "+ " PRIMARY KEY ( STUDENT_FEE_ID, CLASS_FEE_ID ) )" );
        db.execSQL(" create table " + USERS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "USER_NAME TEXT," + " PASSWORD TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CLASS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FEES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }

                // Data add methods

        // Method for add a student

    public boolean addStudent(String firstName, String lastName, String contactNo, String eContactNo, String paymentStatus) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIRST_NAME, firstName);
        contentValues.put(LAST_NAME, lastName);
        contentValues.put(STUDENT_CONTACT_NO, contactNo);
        contentValues.put(EMERGENCY_CONTACT_NO, eContactNo);
        //contentValues.put(FEE_TYPE,feeType);
        contentValues.put(PAYMENT_STATUS,paymentStatus);

        long results  = db.insert(STUDENTS_TABLE,null,contentValues);

        // checking if the insertion is successful
        if (results == -1){
            return false;
        }else {
            return true;
        }
    }

        // Method for add a class

    public boolean addClass(String className, String locationLink, String feePlan) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CLASS_NAME, className);
        contentValues.put(LOCATION_LINK, locationLink);
        //contentValues.put(FEE_PLAN, feePlan);

        long results  = db.insert(CLASS_TABLE,null,contentValues);

        // checking if the insertion is successful
        if (results == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean addStudentToClass(int studentId,int classId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int paymentStatus = 0;
        contentValues.put(STUDENT_FEE_ID, studentId);
        contentValues.put(CLASS_FEE_ID, classId);
        contentValues.put(PAYMENT_STATUS,paymentStatus);
        long results  = db.insert(FEES_TABLE,null,contentValues);

        if (results == -1){
            return false;
        }else {
            return true;
        }
    }
    public void removeStudentFromClass(int studentId,int classId){

        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = { String.valueOf(studentId), String.valueOf(classId) };
        db.delete("FEES_TABLE", "STUDENT_FEE_ID=? AND CLASS_FEE_ID=?", whereArgs);
    }

                //update Methods

        // Method for updating students' data

    public boolean updateStudentData(String id, String firstName,String lastName, String contactNo, String eContactNo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIRST_NAME, firstName);
        contentValues.put(LAST_NAME, lastName);
        contentValues.put(STUDENT_CONTACT_NO, contactNo);
        contentValues.put(EMERGENCY_CONTACT_NO, eContactNo);

        db.update(STUDENTS_TABLE, contentValues, "id = ?", new String[] {id});
        return true;
    }

        // Method for updating class data

//    public boolean updateClassData(String id, String className, String startDate, String feePlan) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(CLASS_NAME, className);
//        contentValues.put(START_DATE, startDate);
//        contentValues.put(FEE_PLAN, feePlan);
//
//
//        db.update(CLASS_TABLE, contentValues, "id = ?", new String[] {id});
//            return true;
//
//    }

    public boolean updatePaymentValue(int studentId, int classId){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int paymentStatus = 1;
        contentValues.put(PAYMENT_STATUS, paymentStatus);
        String[] whereArgs = { String.valueOf(studentId), String.valueOf(classId) };

        int isUpdated = db.update(FEES_TABLE, contentValues, "STUDENT_FEE_ID=? AND CLASS_FEE_ID=?",whereArgs);

        if(isUpdated == -1){
            return false;
        }else{
            return true;
        }
    }



    public Cursor getAllStudentData() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor results = db.rawQuery("select * from " + STUDENTS_TABLE,null);


        return results;
    }

    public Cursor getAllClassData(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor results = db.rawQuery("select * from " + CLASS_TABLE,null);

        return results;
    }

    public ArrayList<Integer> getClassStudent(int classId){

        ArrayList<Integer> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT STUDENT_FEE_ID FROM " + FEES_TABLE + " WHERE CLASS_FEE_ID = " + classId, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("STUDENT_FEE_ID"));

                studentList.add(id);
            } while (cursor.moveToNext());

        }
        return studentList;
    }

    public ArrayList<Integer> getClassStudentPayment(int classId){

        ArrayList<Integer> classPaymentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT PAYMENT_STATUS FROM " + FEES_TABLE + " WHERE CLASS_FEE_ID = " + classId, null);

        if (cursor.moveToFirst()) {
            do {

                @SuppressLint("Range") int paymentStatus = cursor.getInt(cursor.getColumnIndex("PAYMENT_STATUS"));
                classPaymentList.add(paymentStatus);

            } while (cursor.moveToNext());
        }
        return classPaymentList;
    }

    public StudentModel getStudentDetails(int studentId) {
        //getting student's data for student's profile
        int id = studentId;
        StudentModel studentModel = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery("select * from " + STUDENTS_TABLE + " WHERE ID = " + id, null);

        // Check if a record was found
        if (results.moveToFirst()) {

            // Retrieve column values from the cursor
            @SuppressLint("Range") int sId = results.getInt(results.getColumnIndex("ID"));
            @SuppressLint("Range") String firstName = results.getString(results.getColumnIndex("FIRST_NAME"));
            @SuppressLint("Range") String lastName = results.getString(results.getColumnIndex("LAST_NAME"));
            @SuppressLint("Range") String contact = results.getString(results.getColumnIndex("CONTACT_NO"));
            //temporary
            int type = 0;
                studentModel = new StudentModel(sId, firstName, lastName, type,contact);

            // Assigning retrieved values to StudentModel object

        }
        results.close();
        db.close();

        return studentModel;
    }

    public Cursor getClassesOfStudent(int studentId){
        //getting student's class data to the student's profile
        int id = studentId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery("select CLASS_FEE_ID from " + FEES_TABLE + " WHERE ID = " + id, null);
        return null;
    }
    public boolean signIn(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, userName);
        contentValues.put(PASSWORD, password);

        long results  = db.insert(USERS,null,contentValues);

        // checking if the insertion is successful
        if (results == -1){
            return false;
        }else {
            return true;
        }
    }

    @SuppressLint("Range")
//    public boolean login(String userName, String password) {
//        String user, pass;
//        user ="";
//        pass="";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor results;
//        try {
//            String query = "SELECT USER_NAME FROM users WHERE USER_NAME = ? AND PASSWORD = ?";
//            String[] selectionArgs = {userName, password};
//            results = db.rawQuery(query, selectionArgs);
//
//            if (results.moveToFirst()) {
//                // Retrieve column values from the cursor
//                user = results.getString(results.getColumnIndex("USER_NAME"));
//                pass = results.getString(results.getColumnIndex("PASSWORD"));
//            }
//
//            if (user.equals(userName) & pass.equals(password)) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }
    public boolean login(String userName, String password) {
        String user = "";
        String pass = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor results = null;

        try {
            String query = "SELECT USER_NAME, PASSWORD FROM users WHERE USER_NAME = ? AND PASSWORD = ?";
            String[] selectionArgs = {userName, password};
            results = db.rawQuery(query, selectionArgs);

            if (results.moveToFirst()) {
                // Retrieve column values from the cursor
                int userNameIndex = results.getColumnIndex("USER_NAME");
                int passwordIndex = results.getColumnIndex("PASSWORD");

                user = results.getString(userNameIndex);
                pass = results.getString(passwordIndex);
            }

            if (user.equals(userName) && pass.equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (results != null) {
                results.close();
            }
            // Close the database connection
            db.close();
        }
    }



    public boolean monthlyStateChange(){
        SQLiteDatabase db = this.getWritableDatabase();
        String updatingValue;
        updatingValue = "np";
        ContentValues values = new ContentValues();
        values.put(PAYMENT_STATUS,updatingValue);

        try {
            int isUpdated = db.update(FEES_TABLE, values, null, null);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public int recordCount(String tableName){
        String tbName = tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + tbName;
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    @SuppressLint("Range")
    public String getClassLocation(int classId){

        SQLiteDatabase db = this.getReadableDatabase();
        String out;

        try {
            Cursor results = db.rawQuery("select LOCATION_LINK from " + CLASS_TABLE + " WHERE ID = " + classId, null);
            if (results.moveToFirst()) {

                out = results.getString(results.getColumnIndex("LOCATION_LINK"));
                results.close();
            } else {
                out = "n/a";
            }


        } catch (Exception e) {
            out = "n/a";
        }

        return out;
    }
}
