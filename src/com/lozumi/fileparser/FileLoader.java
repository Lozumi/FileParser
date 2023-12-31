package com.lozumi.fileparser;

import java.io.*;
import java.text.ParseException;
import java.util.List;

/**
 * 文件加载器类，用于从文件中读取学生信息并进行解析。
 */
public class FileLoader {
    // standard io streams
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter stdOut = new PrintWriter(System.out);
    PrintWriter stdErr = new PrintWriter(System.err);

    private List<StudentInfo> studentInfoList;

    /**
     * 构造函数，根据文件类型加载相应类型的学生信息。
     *
     * @param filePath 文件路径
     * @param type     文件类型，1表示本科生文件，2表示研究生文件，3表示博士生文件
     */
    public FileLoader(String filePath, int type) {
        try {
            String fileContent = readFile(filePath);

            switch (type) {
                case 1:
                    studentInfoList = new UndergraduateStudentParser().parseStudentInfo(fileContent);
                    break;
                case 2:
                    studentInfoList = new GraduateStudentParserParser().parseStudentInfo(fileContent);
                    break;
                case 3:
                    studentInfoList = new DoctoralStudentParserParser().parseStudentInfo(filePath);
                    break;
                default:
                    break;
            }
        } catch (IOException | ParseException e) {
            stdErr.println("Error loading file: " + e.getMessage());
        }
    }

    /**
     * 从文件中读取内容。
     *
     * @param filePath 文件路径
     * @return 文件中数据
     * @throws IOException          IO异常
     * @throws FileNotFoundException 文件未找到异常
     */
    public static String readFile(String filePath) throws IOException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * 返回学生信息列表。
     *
     * @return 学生信息列表
     */
    public List<StudentInfo> getStudentInfoList() {
        return studentInfoList;
    }
}
