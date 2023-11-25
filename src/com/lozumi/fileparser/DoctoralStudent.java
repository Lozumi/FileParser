package com.lozumi.fileparser;

import java.util.Date;

/**
 * 博士生类，继承自学生信息基类 {@link StudentInfo}。
 */
public class DoctoralStudent extends StudentInfo {
    private String supervisor;
    private String researchFields;

    /**
     * 构造函数，用于创建博士生对象。
     *
     * @param studentNumber 学号
     * @param studentName   姓名
     * @param gender        性别
     * @param birthday      出生日期
     * @param academy       学院
     * @param major         专业
     * @param supervisor    导师
     * @param researchFields 研究生方向
     */
    public DoctoralStudent(String studentNumber, String studentName, char gender, Date birthday, String academy, String major, String supervisor, String researchFields) {
        super(studentNumber, studentName, gender, birthday, academy, major);
        this.supervisor = supervisor;
        this.researchFields = researchFields;
    }

    /**
     * 获取导师姓名。
     *
     * @return 导师姓名
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * 设置导师姓名。
     *
     * @param supervisor 导师姓名
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * 获取研究生方向。
     *
     * @return 研究生方向
     */
    public String getResearchFields() {
        return researchFields;
    }

    /**
     * 设置研究生方向。
     *
     * @param researchFields 研究生方向
     */
    public void setResearchFields(String researchFields) {
        this.researchFields = researchFields;
    }
}
