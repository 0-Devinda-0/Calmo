package com.example.calmo;

public class ClassModel {
    String className;
    int classId;



    public ClassModel(int classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public int getClassId() {
        return classId;
    }
}
