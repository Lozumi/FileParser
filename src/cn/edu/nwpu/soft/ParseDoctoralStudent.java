package cn.edu.nwpu.soft;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParseDoctoralStudent implements ParseStudentInfo{
    public List<StudentInfo> parseStudentInfo(String asd){
        //创建解析器对象
        SAXReader saxReader=new SAXReader();
        List<DoctoralStudent> doctors = new ArrayList<>();
        try{
            //根据user.xml文档生成Document对象
            Document document = saxReader.read("ds.xml");
            //通过document对象获取根节点doctoralStudents
            Element doctoralStudents = document.getRootElement();

            List<Element> studentInfo = doctoralStudents.elements();

            for(Element i : studentInfo)
            {
                //<doctoralStudents>标签的子标签
                String itName = i.getName();
                // 不打印类别子标签 stdOut.println(itName);

                List<Element> studentInfoAttr = i.elements();
                String studentNumber = null;
                String studentName = null;
                char gander = '\0';
                Date birthday = null;
                String academy = null;
                String major = null;
                String supervisor = null;
                String researchFields = null;
                for(int j =0;j<studentInfoAttr.size();j++)
                {
                    //没想好怎么转成java对象,只能用一个一个转换的笨办法了
                    StringBuffer sb = new StringBuffer();
                    // sb.append(j.getName()).append(": ").append(j.getTextTrim());

                    if(j==0)
                        studentNumber = studentInfoAttr.get(j).getTextTrim();
                    else if(j==1)
                        studentName = studentInfoAttr.get(j).getTextTrim();
                    else if(j==2)
                    {String s1 = studentInfoAttr.get(j).getTextTrim();
                        if (s1.equals("男"))
                            gander = '男';
                        else
                            gander = '女';
                    }
                    else if(j==3)
                    {
                        String s2 = studentInfoAttr.get(j).getTextTrim();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
                        birthday = format.parse(s2);
                    }
                    else if(j==4)
                        academy= studentInfoAttr.get(j).getTextTrim();
                    else if(j==5)
                        major = studentInfoAttr.get(j).getTextTrim();
                    else if(j==6)
                        supervisor = studentInfoAttr.get(j).getTextTrim();
                    else if(j==7)
                        researchFields =studentInfoAttr.get(j).getTextTrim();
                }
                DoctoralStudent doc = new DoctoralStudent(studentNumber,studentName,gander,birthday, academy,
                        major,supervisor,researchFields);
                doctors.add(doc);
            }
            List<StudentInfo> studentInfos = new ArrayList<>(doctors);
            return studentInfos;

        }catch(Exception e)
        {
            System.err.println("出现错误！"+e.getMessage());
        }
        return null;
    }

    @Override
    public String parseString(List<StudentInfo> studentInfos) {
        List<DoctoralStudent> doctoralStudents = new ArrayList<>();
        for(StudentInfo studentInfo:studentInfos){
            doctoralStudents.add((DoctoralStudent) studentInfo);
        }
        // Create root element
        Element rootElement = DocumentHelper.createElement("doctoralStudents");
        Document document = DocumentHelper.createDocument(rootElement);

        // Create elements for each DoctoralStudent object
        for (DoctoralStudent student: doctoralStudents) {
            Element studentInfoElement = rootElement.addElement("studentInfo");

            studentInfoElement.addElement("studentNumber").setText(student.getStudentNumber());
            studentInfoElement.addElement("studentName").setText(student.getStudentName());
            studentInfoElement.addElement("gander").setText(String.valueOf(student.getGander()));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            studentInfoElement.addElement("birthday").setText(dateFormat.format(student.getBirthday()));

            studentInfoElement.addElement("academy").setText(student.getAcademy());
            studentInfoElement.addElement("major").setText(student.getMajor());
            studentInfoElement.addElement("supervisor").setText(student.getSupervisor());
            studentInfoElement.addElement("researchFields").setText(student.getResearchFields());
        }

        // Convert Document to XML string
        return document.asXML();
    }

    public static String objectToXml(DoctoralStudent student) {
        // Create root element
        Element rootElement = DocumentHelper.createElement("doctoralStudent");
        Document document = DocumentHelper.createDocument(rootElement);

        // Create elements for the DoctoralStudent object
        Element studentInfoElement = rootElement.addElement("studentInfo");

        studentInfoElement.addElement("studentNumber").setText(student.getStudentNumber());
        studentInfoElement.addElement("studentName").setText(student.getStudentName());
        studentInfoElement.addElement("gander").setText(String.valueOf(student.getGander()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        studentInfoElement.addElement("birthday").setText(dateFormat.format(student.getBirthday()));

        studentInfoElement.addElement("academy").setText(student.getAcademy());
        studentInfoElement.addElement("major").setText(student.getMajor());
        studentInfoElement.addElement("supervisor").setText(student.getSupervisor());
        studentInfoElement.addElement("researchFields").setText(student.getResearchFields());

        // Convert Document to XML string
        return document.asXML();
    }
}