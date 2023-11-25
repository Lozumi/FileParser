package com.lozumi.fileparser;

import java.util.Date;

public class DoctoralStudent extends StudentInfo{
    private String supervisor;
    private String researchFields;

    public DoctoralStudent(String studentNumber, String studentName, char gander, Date birthday, String academy, String major, String supervisor, String researchFields) {
        super(studentNumber, studentName, gander, birthday, academy, major);
        this.supervisor = supervisor;
        this.researchFields = researchFields;
    }

    public String getSupervisor(){
        return supervisor;
    }
    public void setSupervisor(String supervisor){
        this.supervisor = supervisor;
    }
    public String getResearchFields(){
        return researchFields;
    }
    public void setResearchFields(String researchFields){
        this.researchFields = researchFields;
    }
}
