package cn.edu.nwpu.soft;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UndergraduateStudent extends StudentInfo{
    public UndergraduateStudent(String studentNumber, String studentName, char gander, Date birthday, String academy, String major, String tutor) {
        super(studentNumber, studentName, gander, birthday, academy, major);
        this.tutor = tutor;
    }
    private String tutor;
    public String getTutor(){
        return tutor;
    }


    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return getStudentNumber()+'_'+getStudentName()+'_'+getGander()+'_'+sdf.format(getBirthday())+'_'+getAcademy()+'_'+getMajor()+'_'+getTutor()+"\n";
    }
}
