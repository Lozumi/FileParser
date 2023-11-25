package cn.edu.nwpu.soft;

import com.alibaba.fastjson2.JSON;
import org.dom4j.DocumentException;

import javax.print.Doc;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class StudentSubSystem {
    private static BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter stdOut = new PrintWriter(System.out, true);
    private static PrintWriter stdErr = new PrintWriter(System.err, true);

    private List<StudentInfo> studentInfos;
    private List<StudentInfo> undergraduateStudents;
    private List<StudentInfo> graduateStudents;
    private List<StudentInfo> doctoralStudents;
    private Observer undergraduateStudentObserver;
    private Observer graduateStudentObserver;
    private Observer doctoralStudentObservser;

    private StudentSubSystem() throws DocumentException, IOException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        studentInfos = new ArrayList<>();
        loadStudents();

    }

    private void loadStudents() throws DocumentException, IOException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        undergraduateStudents = new FileLoad("us.txt", 1).getStudentInfos();
        studentInfos.addAll(undergraduateStudents);
        graduateStudents = new FileLoad("gs.json", 2).getStudentInfos();
        studentInfos.addAll(graduateStudents);
        doctoralStudents = new FileLoad("ds.xml", 3).getStudentInfos();
        studentInfos.addAll(doctoralStudents);

        undergraduateStudentObserver = new Observer(undergraduateStudents, "us.txt");
        graduateStudentObserver = new Observer(graduateStudents,"gs.json");
        doctoralStudentObservser = new Observer(doctoralStudents,"ds.xml");
    }

    /**
     * Starts the application
     *
     * @param args 传入系统的初始值
     * @throws IOException 抛出错误
     */
    public static void main(String[] args) throws IOException, DocumentException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        StudentSubSystem studentSubSystem = new StudentSubSystem();
        studentSubSystem.run();
    }

    /**
     * Present the user with a menu of options and execute the selected task
     *
     * @throws IOException 当有IOException异常时，抛出异常
     */
    private void run() throws IOException {
        // TODO:
        while (true){
            try{
                String choice = getChoice();
                switch (choice){
                    case "A":
                        showAllStudentInfos();
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
                // 暂停一会以防止菜单错位


            }catch (IOException e){
                stdErr.println(e.getMessage());
            }catch (InterruptedException e){
                stdErr.println("操作被中断");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createDoctoralStudent() throws ParseException, IOException {
        stdErr.println("请输入博士生信息，格式：学号 姓名 性别 生日（格式：yyyy年MM月dd日） 学院 专业 导师 研究生方向");
        String[] in = stdIn.readLine().split(" ");
        Date birthday = null;
        {
            String dateString = in[3];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            birthday = sdf.parse(dateString);
        }
        doctoralStudents.add(new DoctoralStudent(in[0],in[1],in[2].charAt(0),birthday,in[4],in[5],in[6],in[7]));
        doctoralStudentObservser.update("ds.xml", new ParseDoctoralStudent(), doctoralStudents);
    }

    private void createGraduateStudent() throws IOException, ParseException {
        stdErr.println("请输入研究生信息，格式：学号 姓名 性别 生日（格式：yyyy年MM月dd日） 学院 专业 导师");
        String[] in = stdIn.readLine().split(" ");
        Date birthday = null;
        {
            String dateString = in[3];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            birthday = sdf.parse(dateString);
        }
        graduateStudents.add(new GraduateStudent(in[0],in[1],in[2].charAt(0),birthday,in[4],in[5],in[6]));
        graduateStudentObserver.update("gs.json",new ParseGraduateStudent(), graduateStudents);
    }

    private void createUndergraduateStudent() throws IOException, ParseException {
        stdErr.println("请输入本科生信息，格式：学号 姓名 性别 生日（格式：yyyy年MM月dd日） 学院 专业 辅导员");
        String[] in = stdIn.readLine().split(" ");
        Date birthday = null;
        {
            String dateString = in[3];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            birthday = sdf.parse(dateString);
        }
        undergraduateStudents.add(new UndergraduateStudent(in[0],in[1],in[2].charAt(0),birthday,in[4],in[5],in[6]));
        undergraduateStudentObserver.update("us.txt", new ParseUndergraduateStudent(), undergraduateStudents);
    }

    /**按本科生、研究生、博士生的顺序，每类学生再按年龄从小到大进行排序，并按存储格式打印各学生信息
     *
     */
    private void studentSort() throws IOException {
        undergraduateStudents.sort(Comparator.comparing(StudentInfo::getBirthday));
        graduateStudents.sort(Comparator.comparing(StudentInfo::getBirthday));
        doctoralStudents.sort(Comparator.comparing(StudentInfo::getBirthday));

        undergraduateStudentObserver.update("us.txt", new ParseUndergraduateStudent(), undergraduateStudents);
        graduateStudentObserver.update("gs.json",new ParseGraduateStudent(), graduateStudents);
        doctoralStudentObservser.update("ds.xml", new ParseDoctoralStudent(), doctoralStudents);

        showAllStudentInfos();
    }

    private void findStudentByName(String name) {
        for(StudentInfo studentInfo:studentInfos) {
            if (studentInfo.getStudentName().equals(name)){
                String out = "";
                if(studentInfo instanceof UndergraduateStudent){
                    out = studentInfo.toString();
                } else if (studentInfo instanceof GraduateStudent) {
                    out = JSON.toJSONString(studentInfo);
                } else if (studentInfo instanceof DoctoralStudent) {
                    DoctoralStudent doctoralStudent = (DoctoralStudent) studentInfo;
                    out = ParseDoctoralStudent.objectToXml(doctoralStudent);
                }
                stdOut.println(out);
            }
        }
    }

    private void findStudentByID(String id) {
        for(StudentInfo studentInfo:studentInfos){
            if(studentInfo.getStudentNumber().equals(id)){
                String out = "";
                if(studentInfo instanceof UndergraduateStudent){
                    out = studentInfo.toString();
                } else if (studentInfo instanceof GraduateStudent) {
                    out = JSON.toJSONString(studentInfo);
                } else if (studentInfo instanceof DoctoralStudent) {
                    DoctoralStudent doctoralStudent = (DoctoralStudent) studentInfo;
                    out = ParseDoctoralStudent.objectToXml(doctoralStudent);
                }
                stdOut.println(out);
            }
        }
    }

    private void showAllStudentInfos() throws IOException {
        for(String filePath:new String[]{"us.txt", "gs.json", "ds.xml"}) {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line!=null){
                stdOut.println(line);
                line = reader.readLine();
            }
            stdOut.flush();
        }
    }


    /**
     * Display a menu of options and verifies the user's choice
     *
     * @return 返回了用户的选项代码
     * @throws IOException 抛出异常
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
}
