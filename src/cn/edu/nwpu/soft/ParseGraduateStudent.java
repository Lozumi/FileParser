package cn.edu.nwpu.soft;

import com.alibaba.fastjson2.JSON;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将json信息转换为StudentInfo列表
 * 读取json信息
 */
public class ParseGraduateStudent implements ParseStudentInfo{
    @Override
    public List<StudentInfo> parseStudentInfo(String fileContent) throws ParseException {
        return new ArrayList<>(JSON.parseArray(fileContent,GraduateStudent.class));
    }

    @Override
    public String parseString(List<StudentInfo> studentInfos) {
        List<GraduateStudent> graduateStudents = new ArrayList<>();
        for(StudentInfo studentInfo:studentInfos){
            graduateStudents.add((GraduateStudent) studentInfo);
        }
        return JSON.toJSONString(graduateStudents);
    }
}
