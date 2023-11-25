package com.lozumi.fileparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UndergraduateStudentParser implements StudentInfoParser {

    @Override
    public List<StudentInfo> parseStudentInfo(String fileContent) throws ParseException {
        List<StudentInfo> studentInfoList = new ArrayList<>();
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            lineProcess(studentInfoList, line);
        }
        return studentInfoList;
    }

    private void lineProcess(List<StudentInfo> studentInfoList, String line) throws ParseException {
        UndergraduateStudent undergraduateStudent;
        String[] infos = line.split("_");

        Date birthday = parseDate(infos[3]);
        undergraduateStudent = new UndergraduateStudent(infos[0], infos[1], infos[2].charAt(0), birthday, infos[4], infos[5], infos[6]);
        studentInfoList.add(undergraduateStudent);
    }

    @Override
    public String parseString(List<StudentInfo> studentInfoList) {
        StringBuilder sb = new StringBuilder();
        for (StudentInfo studentInfo : studentInfoList) {
            sb.append(studentInfo.toString());
        }
        return sb.toString();
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.parse(dateString);
    }
}
