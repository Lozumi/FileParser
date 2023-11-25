package com.lozumi.fileparser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 观察者类，用于观察并更新学生信息。
 */
public class Observer {
    private List<StudentInfo> studentInfoList;
    private PrintWriter output;

    /**
     * 构造一个观察者对象。
     *
     * @param studentInfoList 要观察的学生信息列表
     * @param fileName        输出文件的路径
     * @throws IOException 如果发生文件操作异常
     */
    public Observer(List<StudentInfo> studentInfoList, String fileName) throws IOException {
        this.studentInfoList = studentInfoList;
        output = new PrintWriter(new FileWriter(fileName, true), true);  // 设置为自动刷新
    }

    /**
     * 更新观察者的学生信息，并将更新后的信息写入文件。
     *
     * @param fileName          要更新的文件路径
     * @param studentInfoParser 学生信息解析器
     * @param studentInfoList   更新后的学生信息列表
     * @throws IOException 如果发生文件操作异常
     */
    public void update(String fileName, StudentInfoParser studentInfoParser, List<StudentInfo> studentInfoList) throws IOException {
        PrintWriter stringWriter = new PrintWriter(new FileWriter(fileName));
        stringWriter.print(studentInfoParser.parseString(studentInfoList));
        stringWriter.close();
    }

    /**
     * 在适当的时候关闭输出流。
     */
    public void close() {
        if (output != null) {
            output.close();
        }
    }
}
