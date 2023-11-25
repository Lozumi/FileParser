package com.lozumi.fileparser;

import com.alibaba.fastjson2.JSON;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将 JSON 信息转换为 {@link StudentInfo} 列表的解析器。
 * 读取 JSON 信息并转换为相应的研究生学生信息列表。
 */
public class GraduateStudentParserParser implements StudentInfoParser {

    /**
     * 将 JSON 格式的文件内容解析为 {@link GraduateStudent} 列表。
     *
     * @param fileContent JSON 格式的文件内容
     * @return 研究生学生信息列表
     * @throws ParseException 如果解析过程中发生错误
     */
    @Override
    public List<StudentInfo> parseStudentInfo(String fileContent) throws ParseException {
        try {
            return new ArrayList<>(JSON.parseArray(fileContent, GraduateStudent.class));
        } catch (com.alibaba.fastjson2.JSONException e) {
            throw new ParseException("解析 JSON 错误：" + e.getMessage(), 0);
        }
    }

    /**
     * 将学生信息列表转换为 JSON 格式的字符串。
     *
     * @param studentInfoList 学生信息列表
     * @return JSON 格式的字符串
     */
    @Override
    public String parseString(List<StudentInfo> studentInfoList) {
        return JSON.toJSONString(studentInfoList);
    }
}
