package cn.edu.nwpu.soft;

import org.dom4j.DocumentException;

import javax.print.Doc;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileLoad {
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter stdOut = new PrintWriter(System.out);
    PrintWriter stdErr = new PrintWriter(System.err);

    private List<StudentInfo> studentInfos;
    private ParseStudentInfo parseStudentInfo;

    public FileLoad(String fileName, int type) throws IOException, ParseException, DocumentException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String fileContent = readFileToString(fileName);

        /**
         * 1 us.txt
         * 2 gs.json
         * 3 ds.xml
         */
        switch (type) {
            case 1:
                parseStudentInfo = new ParseUndergraduateStudent();
                studentInfos = parseStudentInfo.parseStudentInfo(fileContent);
                break;
            case 2:
                parseStudentInfo = new ParseGraduateStudent();
                studentInfos = parseStudentInfo.parseStudentInfo(fileContent);
                break;
            case 3:
                studentInfos =  new ParseDoctoralStudent().parseStudentInfo("asd");
                break;
            default:
                break;
        }
    }

    /**
     * 从文件中读取内容
     *
     * @param filePath 文件路径
     * @return 文件中数据
     * @throws IOException IO异常
     */
    public static String readFileToString(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }

        return contentBuilder.toString();
    }

    /**
     * 返回学生信息列表
     * @return 学生信息列表
     */
    public List<StudentInfo> getStudentInfos(){
        return studentInfos;
    }
}
