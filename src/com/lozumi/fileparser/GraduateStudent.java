package com.lozumi.fileparser;

import java.util.Date;

/**
 * 研究生类，继承自学生信息类 {@link StudentInfo}。
 */
public class GraduateStudent extends StudentInfo {
    private String supervisor;

    /**
     * 构造函数，用于创建研究生对象。
     *
     * @param studentNumber 学号
     * @param studentName   姓名
     * @param gender        性别
     * @param birthday      出生日期
     * @param academy       学院
     * @param major         专业
     * @param supervisor    导师
     */
    public GraduateStudent(String studentNumber, String studentName, char gender, Date birthday, String academy, String major, String supervisor) {
        super(studentNumber, studentName, gender, birthday, academy, major);
        this.supervisor = supervisor;
    }

    /**
     * 获取导师信息。
     *
     * @return 导师
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * 设置导师信息。
     *
     * @param supervisor 导师
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
}
