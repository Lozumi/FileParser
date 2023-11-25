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
        this.output = new PrintWriter(new FileWriter(fileName, true));
    }

    public void update(String fileName, StudentInfoParser studentInfoParser, List<StudentInfo> studentInfoList) {
        try (PrintWriter stringWriter = new PrintWriter(new FileWriter(fileName))) {
            stringWriter.print(studentInfoParser.parseString(studentInfoList));
        } catch (IOException e) {
            // 处理异常，这里可以根据实际情况选择记录日志或者抛出运行时异常
            e.printStackTrace();
        }
    }

    // 如果需要，在适当的时候关闭 PrintWriter
    public void close() {
        if (output != null) {
            output.close();
        }
    }
}
