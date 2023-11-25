package com.lozumi.fileparser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Observer {
    private List<StudentInfo> studentInfoList;
    private PrintWriter output;

    public Observer(List<StudentInfo> studentInfoList, String fileName) throws IOException {
        this.studentInfoList = studentInfoList;
        output = new PrintWriter(new FileWriter(fileName, true), true);  // 设置为自动刷新
    }

    public void update(String fileName, StudentInfoParser studentInfoParser, List<StudentInfo> studentInfoList) throws IOException {
        PrintWriter stringWriter = new PrintWriter(new FileWriter(fileName));
        stringWriter.print(studentInfoParser.parseString(studentInfoList));
        stringWriter.close();
    }

    // 如果需要，在适当的时候关闭 PrintWriter
    public void close() {
        if (output != null) {
            output.close();
        }
    }
}
