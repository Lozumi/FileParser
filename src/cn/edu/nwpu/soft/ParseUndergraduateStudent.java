package cn.edu.nwpu.soft;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParseUndergraduateStudent implements ParseStudentInfo{
    @Override
    public List<StudentInfo> parseStudentInfo(String fileContent) throws ParseException {
        List<StudentInfo> studentInfos = new ArrayList<>();

        String[] lines = fileContent.split("\n");

        for(String line : lines){
            lineProcess(studentInfos, line);
        }
        return studentInfos;
    }

    private void lineProcess(List<StudentInfo> studentInfos , String line) throws ParseException {
        UndergraduateStudent undergraduateStudent;
        String[] infos = line.split("_");

        // 将字符转换为Date
        Date birthday = null;
        {
            String dateString = infos[3];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            birthday = sdf.parse(dateString);
        }

        undergraduateStudent = new UndergraduateStudent(infos[0],infos[1],infos[2].charAt(0), birthday, infos[4], infos[5],infos[6]);
        studentInfos.add(undergraduateStudent);
    }
    @Override
    public String parseString(List<StudentInfo> studentInfos){
        StringBuilder sb = new StringBuilder();
        for(StudentInfo studentInfo : studentInfos){
            sb.append(studentInfo.toString());
        }
        return sb.toString();
    }
}
