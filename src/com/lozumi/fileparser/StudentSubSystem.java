package com.lozumi.fileparser;

import com.alibaba.fastjson2.JSON;
import org.dom4j.DocumentException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 学生信息子系统类，用于管理学生信息。
 */
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

    /**
     * 构造函数，初始化学生信息列表和观察者对象。
     *
     * @throws DocumentException       如果文档解析异常
     * @throws IOException            如果发生 I/O 异常
     * @throws ParseException         如果解析异常
     * @throws InvocationTargetException 如果调用目标异常
     * @throws InstantiationException    如果实例化异常
     * @throws IllegalAccessException    如果访问权限异常
     */
    private StudentSubSystem() throws DocumentException, IOException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        studentInfoList = new ArrayList<>();
        loadStudents();
    }

    /**
     * 加载学生信息，包括本科生、研究生和博士生。
     *
     * @throws DocumentException       如果文档解析异常
     * @throws IOException            如果发生 I/O 异常
     * @throws ParseException         如果解析异常
     * @throws InvocationTargetException 如果调用目标异常
     * @throws InstantiationException    如果实例化异常
     * @throws IllegalAccessException    如果访问权限异常
     */
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

    /**
     * 主程序入口，启动学生信息子系统。
     *
     * @param args 命令行参数
     * @throws IOException            如果发生 I/O 异常
     * @throws DocumentException       如果文档解析异常
     * @throws ParseException         如果解析异常
     * @throws InvocationTargetException 如果调用目标异常
     * @throws InstantiationException    如果实例化异常
     * @throws IllegalAccessException    如果访问权限异常
     */
    public static void main(String[] args) throws IOException, DocumentException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        StudentSubSystem studentSubSystem = new StudentSubSystem();
        studentSubSystem.run();
    }

    /**
     * 学生信息子系统的运行方法，处理用户交互。
     *
     * @throws IOException            如果发生 I/O 异常
     * @throws InterruptedException   如果线程中断异常
     * @throws ParseException         如果解析异常
     * @throws DocumentException       如果文档解析异常
     * @throws InvocationTargetException 如果调用目标异常
     * @throws InstantiationException    如果实例化异常
     * @throws IllegalAccessException    如果访问权限异常
     */
    private void run() throws IOException, InterruptedException, ParseException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException {
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

    /**
     * 创建一个博士生。
     *
     * @throws ParseException         如果解析异常
     * @throws IOException            如果发生 I/O 异常
     * @throws DocumentException       如果文档解析异常
     * @throws InvocationTargetException 如果调用目标异常
     * @throws InstantiationException    如果实例化异常
     * @throws IllegalAccessException    如果访问权限异常
     */
    private void createDoctoralStudent() throws ParseException, IOException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException {
        stdErr.println("请输入新增博士生信息，格式为：学号 姓名 性别 生日（格式：yyyy年MM月dd日） 学院 专业 导师 研究生方向");
        String[] in = stdIn.readLine().split(" ");
        Date birthday = parseDate(in[3]);
        doctoralStudents.add(new DoctoralStudent(in[0], in[1], in[2].charAt(0), birthday, in[4], in[5], in[6], in[7]));
        doctoralStudentObserver.update("ds.xml", new DoctoralStudentParserParser(), doctoralStudents);
        loadStudents();  // 刷新学生信息
    }

    /**
     * 创建一个研究生。
     *
     * @throws IOException            如果发生 I/O 异常
     * @throws ParseException         如果解析异常
     * @throws DocumentException       如果文档解析异常
     * @throws InvocationTargetException 如果调用目标异常
     * @throws InstantiationException    如果实例化异常
     * @throws IllegalAccessException    如果访问权限异常
     */
    private void createGraduateStudent() throws IOException, ParseException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException {
        stdErr.println("请输入新增研究生信息，格式为：学号 姓名 性别 生日（格式：yyyy年MM月dd日） 学院 专业 导师");
        String[] in = stdIn.readLine().split(" ");
        Date birthday = parseDate(in[3]);
        graduateStudents.add(new GraduateStudent(in[0], in[1], in[2].charAt(0), birthday, in[4], in[5], in[6]));
        graduateStudentObserver.update("gs.json", new GraduateStudentParserParser(), graduateStudents);
        loadStudents();  // 刷新学生信息
    }

    /**
     * 创建一个本科生。
     *
     * @throws IOException            如果发生 I/O 异常
     * @throws ParseException         如果解析异常
     * @throws DocumentException       如果文档解析异常
     * @throws InvocationTargetException 如果调用目标异常
     * @throws InstantiationException    如果实例化异常
     * @throws IllegalAccessException    如果访问权限异常
     */
    private void createUndergraduateStudent() throws IOException, ParseException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException {
        stdErr.println("请输入新增本科生信息，格式为：学号 姓名 性别 生日（格式：yyyy年MM月dd日） 学院 专业 辅导员");
        String[] in = stdIn.readLine().split(" ");
        Date birthday = parseDate(in[3]);
        undergraduateStudents.add(new UndergraduateStudent(in[0], in[1], in[2].charAt(0), birthday, in[4], in[5], in[6]));
        undergraduateStudentObserver.update("us.txt", new UndergraduateStudentParser(), undergraduateStudents);
        loadStudents();  // 刷新学生信息
    }

    /**
     * 学生排序并更新观察者。
     *
     * @throws IOException            如果发生 I/O 异常
     * @throws DocumentException       如果文档解析异常
     * @throws ParseException         如果解析异常
     * @throws InvocationTargetException 如果调用目标异常
     * @throws InstantiationException    如果实例化异常
     * @throws IllegalAccessException    如果访问权限异常
     */
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

    /**
     * 根据姓名查找学生。
     *
     * @param name 学生姓名
     */
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

    /**
     * 根据学号查找学生。
     *
     * @param id 学号
     */
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

    /**
     * 打印所有学生信息。
     */
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

    /**
     * 格式化并打印本科生信息。
     *
     * @param undergraduateStudent 本科生对象
     */
    private void formatAndPrintUndergraduate(UndergraduateStudent undergraduateStudent) {
        stdOut.println(undergraduateStudent.toString());
    }

    /**
     * 格式化并打印研究生信息。
     *
     * @param graduateStudent 研究生对象
     */
    private void formatAndPrintGraduate(GraduateStudent graduateStudent) {
        stdOut.println(JSON.toJSONString(graduateStudent));
    }

    /**
     * 格式化并打印博士生信息。
     *
     * @param doctoralStudent 博士生对象
     */
    private void formatAndPrintDoctoral(DoctoralStudent doctoralStudent) {
        stdOut.println(DoctoralStudentParserParser.objectToXml(doctoralStudent));
    }

    /**
     * 获取用户选择。
     *
     * @return 用户输入的选择
     * @throws IOException 如果发生 I/O 异常
     */
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

    /**
     * 将字符串日期解析为 Date 对象。
     *
     * @param dateString 字符串日期
     * @return Date 对象
     * @throws ParseException 如果解析异常
     */
    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.parse(dateString);
    }
}
