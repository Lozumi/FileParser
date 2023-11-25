package com.lozumi.fileparser;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.Date;

/**
 * 学生信息抽象类，包含学生的基本信息。
 * 该抽象类定义了学生的学号、姓名、性别、生日、学院和专业等基本属性。
 * 其中，生日使用了 {@link JSONField} 注解，指定了日期的格式。
 */
public abstract class StudentInfo {
	private String studentNumber;
	private String studentName;
	private char gender;
	@JSONField(format = "yyyy年MM月dd日")
	private Date birthday;
	private String academy;
	private String major;

	/**
	 * 构造一个学生信息对象。
	 *
	 * @param studentNumber 学号
	 * @param studentName   姓名
	 * @param gender        性别
	 * @param birthday      生日
	 * @param academy       学院
	 * @param major         专业
	 */
	public StudentInfo(String studentNumber, String studentName, char gender, Date birthday, String academy,
					   String major) {
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.gender = gender;
		this.birthday = birthday;
		this.academy = academy;
		this.major = major;
	}

	/**
	 * 默认构造函数，用于子类扩展。
	 */
	public StudentInfo() {
	}

	/**
	 * 获取学生的学号。
	 *
	 * @return 学号
	 */
	public String getStudentNumber() {
		return studentNumber;
	}

	/**
	 * 设置学生的学号。
	 *
	 * @param studentNumber 学号
	 */
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	/**
	 * 获取学生的姓名。
	 *
	 * @return 姓名
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * 设置学生的姓名。
	 *
	 * @param studentName 姓名
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * 获取学生的性别。
	 *
	 * @return 性别
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * 设置学生的性别。
	 *
	 * @param gender 性别
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * 获取学生的生日。
	 *
	 * @return 生日
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 设置学生的生日。
	 *
	 * @param birthday 生日
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取学生所在学院。
	 *
	 * @return 学院
	 */
	public String getAcademy() {
		return academy;
	}

	/**
	 * 设置学生所在学院。
	 *
	 * @param academy 学院
	 */
	public void setAcademy(String academy) {
		this.academy = academy;
	}

	/**
	 * 获取学生的专业。
	 *
	 * @return 专业
	 */
	public String getMajor() {
		return major;
	}

	/**
	 * 设置学生的专业。
	 *
	 * @param major 专业
	 */
	public void setMajor(String major) {
		this.major = major;
	}
}
