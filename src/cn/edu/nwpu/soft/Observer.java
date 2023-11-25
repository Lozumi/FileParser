package cn.edu.nwpu.soft;

import java.io.*;
import java.util.List;

public class Observer {
    private List<StudentInfo> studentInfos;
    private PrintWriter output;
    public Observer(List<StudentInfo> studentInfos,String fileName) throws IOException {
        this.studentInfos = studentInfos;
        output = new PrintWriter(new FileWriter(fileName, true));
    }

    public void update(String fileName, ParseStudentInfo parseStudentInfo, List<StudentInfo> studentInfos) throws IOException {
        PrintWriter stringWriter = new PrintWriter(new FileWriter(fileName));
        stringWriter.print(parseStudentInfo.parseString(studentInfos));
        stringWriter.close();
    }
}