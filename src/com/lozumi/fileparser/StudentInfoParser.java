package com.lozumi.fileparser;

import java.text.ParseException;
import java.util.List;

/**
 * 解析学生信息的接口
 */
public interface StudentInfoParser {

    /**
     * 解析文件内容并返回学生信息列表
     *
     * @param fileContent 文件内容
     * @return 学生信息列表
     * @throws ParseException 如果解析过程中发生异常
     */
    List<StudentInfo> parseStudentInfo(String fileContent) throws ParseException;

    /**
     * 将学生信息列表转换为字符串
     *
     * @param studentInfoList 学生信息列表
     * @return 转换后的字符串
     */
    String parseString(List<StudentInfo> studentInfoList);
}
