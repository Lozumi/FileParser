package com.lozumi.fileparser;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.StringWriter;

public class DoctoralStudentParserParser implements StudentInfoParser {

    @Override
    public List<StudentInfo> parseStudentInfo(String filePath) {
        SAXReader saxReader = new SAXReader();
        List<DoctoralStudent> doctors = new ArrayList<>();
        try {
            // 根据传入的文件路径生成Document对象
            Document document = saxReader.read(filePath);

            // 通过document对象获取根节点doctoralStudents
            Element rootElement = document.getRootElement();

            List<Element> studentInfo = rootElement.elements();

            for (Element i : studentInfo) {
                lineProcess(doctors, i);
            }

            List<StudentInfo> studentInfoList = new ArrayList<>(doctors);
            return studentInfoList;

        } catch (Exception e) {
            System.err.println("出现错误！" + e.getMessage());
        }
        return null;
    }

    @Override
    public String parseString(List<StudentInfo> studentInfoList) {
        List<DoctoralStudent> doctoralStudents = new ArrayList<>();
        for (StudentInfo studentInfo : studentInfoList) {
            doctoralStudents.add((DoctoralStudent) studentInfo);
        }

        // Create root element
        Element rootElement = DocumentHelper.createElement("doctoralStudents");

        // Create elements for each DoctoralStudent object
        for (DoctoralStudent student : doctoralStudents) {
            Element studentInfoElement = rootElement.addElement("studentInfo");

            studentInfoElement.addElement("studentNumber").setText(student.getStudentNumber());
            studentInfoElement.addElement("studentName").setText(student.getStudentName());
            studentInfoElement.addElement("gander").setText(String.valueOf(student.getGender()));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            studentInfoElement.addElement("birthday").setText(dateFormat.format(student.getBirthday()));

            studentInfoElement.addElement("academy").setText(student.getAcademy());
            studentInfoElement.addElement("major").setText(student.getMajor());
            studentInfoElement.addElement("supervisor").setText(student.getSupervisor());
            studentInfoElement.addElement("researchFields").setText(student.getResearchFields());
        }

        // Convert Document to XML string
        return rootElement.asXML();
    }

    private void lineProcess(List<DoctoralStudent> studentInfoList, Element element) throws ParseException {
        // 简化属性赋值
        String studentNumber = element.elementText("studentNumber");
        String studentName = element.elementText("studentName");
        char gander = element.elementText("gander").charAt(0);
        Date birthday = new SimpleDateFormat("yyyy年MM月dd日").parse(element.elementText("birthday"));
        String academy = element.elementText("academy");
        String major = element.elementText("major");
        String supervisor = element.elementText("supervisor");
        String researchFields = element.elementText("researchFields");

        DoctoralStudent doc = new DoctoralStudent(studentNumber, studentName, gander, birthday, academy,
                major, supervisor, researchFields);
        studentInfoList.add(doc);
    }

    public static String objectToXml(DoctoralStudent student) {
        // Create root element
        Element rootElement = DocumentHelper.createElement("doctoralStudent");
        Document document = DocumentHelper.createDocument(rootElement);

        // Create elements for the DoctoralStudent object
        Element studentInfoElement = rootElement.addElement("studentInfo");

        studentInfoElement.addElement("studentNumber").setText(student.getStudentNumber());
        studentInfoElement.addElement("studentName").setText(student.getStudentName());
        studentInfoElement.addElement("gander").setText(String.valueOf(student.getGender()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        studentInfoElement.addElement("birthday").setText(dateFormat.format(student.getBirthday()));

        studentInfoElement.addElement("academy").setText(student.getAcademy());
        studentInfoElement.addElement("major").setText(student.getMajor());
        studentInfoElement.addElement("supervisor").setText(student.getSupervisor());
        studentInfoElement.addElement("researchFields").setText(student.getResearchFields());

        // 使用 OutputFormat 设置输出格式，禁用 XML 头部声明
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setSuppressDeclaration(true);

        // 将 Document 写入 StringWriter
        StringWriter writer = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(writer, format);

        try {
            xmlWriter.write(document);
            xmlWriter.close();

            // 输出 XML 字符串
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
