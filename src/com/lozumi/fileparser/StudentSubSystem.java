package com.lozumi.fileparser;

import com.alibaba.fastjson2.JSON;
import org.dom4j.DocumentException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentSubSystem {

    private static BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter stdOut = new PrintWriter(System.out, true);
    private static PrintWriter stdErr = new PrintWriter(System.err, true);

    private List<StudentInfo> studentInfoList;
    private List<StudentInfo> undergraduateStudents;
    private List<StudentInfo> graduateStudents;
    private List<StudentInfo> doctoralStudents;
    private Observer undergraduateStudentObserver;
    private Observer graduateStudentObserver;
    private Observer doctoralStudentObserver;

    private StudentSubSystem() throws DocumentException, IOException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        studentInfoList = new ArrayList<>();
        loadStudents();
    }

    private void loadStudents() throws DocumentException, IOException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        undergraduateStudents = new FileLoader("us.txt", 1).getStudentInfoList();
        studentInfoList.clear();
        studentInfoList.addAll(undergraduateStudents);

        graduateStudents = new FileLoader("gs.json", 2).getStudentInfoList();
        studentInfoList.addAll(graduateStudents);

        doctoralStudents = new FileLoader("ds.xml", 3).getStudentInfoList();
        studentInfoList.addAll(doctoralStudents);

        undergraduateStudentObserver = new Observer(undergraduateStudents, "us.txt");
        graduateStudentObserver = new Observer(graduateStudents, "gs.json");
        doctoralStudentObserver = new Observer(doctoralStudents, "ds.xml");
    }

    public static void main(String[] args) throws IOException, DocumentException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        StudentSubSystem studentSubSystem = new StudentSubSystem();
        studentSubSystem.run();
    }

    private void run() throws IOException {
        while (true) {
            try {
                String choice = getChoice();
                switch (choice) {
                    case "A":
                        showAllStudentsInfos();
                        break;
                    case "B":
                        createUndergraduateStudent();
                        break;
                    case "C":
                        createGraduateStudent();
                        break;
                    case "D":
                        createDoctoralStudent();
                        break;
                    case "E":
                        stdErr.println("请输入学号：");
                        String id = stdIn.readLine();
                        findStudentByID(id);
                        break;
                    case "F":
                        stdErr.println("请输入名字：");
                        String name = stdIn.readLine();
                        findStudentByName(name);
                        break;
                    case "G":
                        studentSort();
                        break;
                    case "H":
                        return;
                    default:
                        stdErr.println("请输入正确选项字母");
                        break;
                }
                Thread.sleep(400);
            } catch (IOException e) {
                stdErr.println(e.getMessage());
            } catch (InterruptedException e) {
                stdErr.println("操作被中断");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createDoctoralStudent() throws ParseException, IOException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException {
        stdErr.println("请输入博士生信息，格式：学号 姓名 性别 生日（格式：yyyy年MM月dd日） 学院 专业 导师 研究生方向");
        String[] in = stdIn.readLine().split(" ");
        Date birthday = parseDate(in[3]);
        doctoralStudents.add(new DoctoralStudent(in[0], in[1], in[2].charAt(0), birthday, in[4], in[5], in[6], in[7]));
        doctoralStudentObserver.update("ds.xml", new DoctoralStudentParserParser(), doctoralStudents);
        loadStudents();  // 刷新学生信息
    }

    private void createGraduateStudent() throws IOException, ParseException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException {
        stdErr.println("请输入研究生信息，格式：学号 姓名 性别 生日（格式：yyyy年MM月dd日） 学院 专业 导师");
        String[] in = stdIn.readLine().split(" ");
        Date birthday = parseDate(in[3]);
        graduateStudents.add(new GraduateStudent(in[0], in[1], in[2].charAt(0), birthday, in[4], in[5], in[6]));
        graduateStudentObserver.update("gs.json", new GraduateStudentParserParser(), graduateStudents);
        loadStudents();  // 刷新学生信息
    }

    private void createUndergraduateStudent() throws IOException, ParseException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException {
        stdErr.println("请输入本科生信息，格式：学号 姓名 性别 生日（格式：yyyy年MM月dd日） 学院 专业 辅导员");
        String[] in = stdIn.readLine().split(" ");
        Date birthday = parseDate(in[3]);
        undergraduateStudents.add(new UndergraduateStudent(in[0], in[1], in[2].charAt(0), birthday, in[4], in[5], in[6]));
        undergraduateStudentObserver.update("us.txt", new UndergraduateStudentParser(), undergraduateStudents);
        loadStudents();  // 刷新学生信息
    }

    private void studentSort() throws IOException, DocumentException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        undergraduateStudents.sort(Comparator.comparing(StudentInfo::getBirthday));
        graduateStudents.sort(Comparator.comparing(StudentInfo::getBirthday));
        doctoralStudents.sort(Comparator.comparing(StudentInfo::getBirthday));

        undergraduateStudentObserver.update("us.txt", new UndergraduateStudentParser(), undergraduateStudents);
        graduateStudentObserver.update("gs.json", new GraduateStudentParserParser(), graduateStudents);
        doctoralStudentObserver.update("ds.xml", new DoctoralStudentParserParser(), doctoralStudents);

        loadStudents();  // 刷新学生信息
        showAllStudentsInfos();
    }

    private void findStudentByName(String name) {
        boolean found = false;
        for (StudentInfo studentInfo : studentInfoList) {
            try {
                if (studentInfo.getStudentName().equals(name)) {
                    String out = "";
                    if (studentInfo instanceof UndergraduateStudent) {
                        out = studentInfo.toString();
                    } else if (studentInfo instanceof GraduateStudent) {
                        out = JSON.toJSONString(studentInfo);
                    } else if (studentInfo instanceof DoctoralStudent) {
                        DoctoralStudent doctoralStudent = (DoctoralStudent) studentInfo;
                        out = DoctoralStudentParserParser.objectToXml(doctoralStudent);
                    }
                    stdOut.println(out);
                    found = true;
                }
            } catch (Exception e) {
                stdErr.println("错误信息: " + e.getMessage());
            }
        }

        if (!found) {
            stdOut.println("未找到该姓名的学生！");
        }
    }

    private void findStudentByID(String id) {
        for (StudentInfo studentInfo : studentInfoList) {
            if (studentInfo.getStudentNumber().equals(id)) {
                String out = "";
                if (studentInfo instanceof UndergraduateStudent) {
                    out = studentInfo.toString();
                } else if (studentInfo instanceof GraduateStudent) {
                    out = JSON.toJSONString(studentInfo);
                } else if (studentInfo instanceof DoctoralStudent) {
                    DoctoralStudent doctoralStudent = (DoctoralStudent) studentInfo;
                    out = DoctoralStudentParserParser.objectToXml(doctoralStudent);
                }
                stdOut.println(out);
                return;  // 找到匹配的学生后，直接返回，避免继续循环
            }
        }
        stdErr.println("未找到学号为 " + id + " 的学生信息");
    }

    private void showAllStudentsInfos() {
        for (StudentInfo studentInfo : studentInfoList) {
            if (studentInfo instanceof UndergraduateStudent) {
                formatAndPrintUndergraduate((UndergraduateStudent) studentInfo);
            } else if (studentInfo instanceof GraduateStudent) {
                formatAndPrintGraduate((GraduateStudent) studentInfo);
            } else if (studentInfo instanceof DoctoralStudent) {
                formatAndPrintDoctoral((DoctoralStudent) studentInfo);
            }
        }
    }

    private void formatAndPrintUndergraduate(UndergraduateStudent undergraduateStudent) {
        //stdOut.println("========== Undergraduate Student Info ==========");
        stdOut.println(undergraduateStudent.toString());
        //stdOut.println("=============================================");
    }

    private void formatAndPrintGraduate(GraduateStudent graduateStudent) {
        //stdOut.println("========== Graduate Student Info ==========");
        stdOut.println(JSON.toJSONString(graduateStudent));
        //stdOut.println("==========================================");
    }

    private void formatAndPrintDoctoral(DoctoralStudent doctoralStudent) {
        //stdOut.println("========== Doctoral Student Info ==========");
        stdOut.println(DoctoralStudentParserParser.objectToXml(doctoralStudent));
        //stdOut.println("=========================================");
    }


    private String getChoice() throws IOException {
        String input = null;
        try {
            stdErr.println();
            stdErr.print("[A]打印全部学生信息\n" +
                    "[B]新增一个本科生\n" +
                    "[C]新增一个研究生\n" +
                    "[D]新增一个博士生\n" +
                    "[E]按学号查找学生\n" +
                    "[F]按姓名查找学生\n" +
                    "[G]学生排序\n" +
                    "[H]退出程序\n");
            stdErr.flush();

            input = stdIn.readLine();
        } catch (NumberFormatException nfe) {
            stdErr.println(nfe);
        }
        return input;
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.parse(dateString);
    }
}
