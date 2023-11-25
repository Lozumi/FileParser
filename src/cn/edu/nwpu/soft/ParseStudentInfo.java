package cn.edu.nwpu.soft;

import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

public interface ParseStudentInfo {
    List<StudentInfo> parseStudentInfo(String fileContent) throws ParseException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException;
    String parseString(List<StudentInfo> studentInfos);
}
