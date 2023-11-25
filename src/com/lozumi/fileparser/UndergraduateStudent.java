package com.lozumi.fileparser;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本科生类，继承自学生信息类 {@link StudentInfo}。
 */
public class UndergraduateStudent extends StudentInfo {

    private String tutor; // 导师信息

    /**
     * 构造本科生对象。
     *
     * @param studentNumber 学号
     * @param studentName   姓名
     * @param gender        性别
     * @param birthday      生日
     * @param academy       学院
     * @param major         专业
     * @param tutor         导师
     */
    public UndergraduateStudent(String studentNumber, String studentName, char gender, Date birthday, String academy, String major, String tutor) {
        super(studentNumber, studentName, gender, birthday, academy, major);
        this.tutor = tutor;
    }

    /**
     * 获取本科生导师信息。
     *
     * @return 导师信息
     */
    public String getTutor() {
        return tutor;
    }

    /**
     * 重写 toString 方法，以字符串形式返回本科生的信息。
     *
     * @return 字符串表示的本科生信息
     */
    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return getStudentNumber() + '_' + getStudentName() + '_' + getGender() + '_' + simpleDateFormat.format(getBirthday()) + '_'
                + getAcademy() + '_' + getMajor() + '_' + getTutor();
    }
}
