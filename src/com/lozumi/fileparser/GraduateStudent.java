package com.lozumi.fileparser;

import java.util.Date;

public class GraduateStudent extends StudentInfo{
    private String supervisor;

    public GraduateStudent(String studentNumber, String studentName, char gander, Date birthday, String academy, String major, String supervisor) {
        super(studentNumber, studentName, gander, birthday, academy, major);
        this.supervisor = supervisor;
    }

    public String getSupervisor(){
        return supervisor;
    }
    public void setSupervisor(String supervisor){
        this.supervisor = supervisor;
    }
}
