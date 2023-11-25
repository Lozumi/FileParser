package com.lozumi.fileparser;

import com.alibaba.fastjson2.JSON;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将 JSON 信息转换为 StudentInfo 列表
 * 读取 JSON 信息
 */
public class GraduateStudentParserParser implements StudentInfoParser {
    @Override
    public List<StudentInfo> parseStudentInfo(String fileContent) throws ParseException {
        try {
            return new ArrayList<>(JSON.parseArray(fileContent, GraduateStudent.class));
        } catch (com.alibaba.fastjson2.JSONException e) {
            throw new ParseException("Error parsing JSON: " + e.getMessage(), 0);
        }
    }

    @Override
    public String parseString(List<StudentInfo> studentInfoList) {
        return JSON.toJSONString(studentInfoList);
    }
}
