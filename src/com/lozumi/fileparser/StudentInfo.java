package com.lozumi.fileparser;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * 
 * @author wben
 *
 */
public abstract class StudentInfo {
	private String studentNumber;
	private String studentName;
	private char gender;
	@JSONField(format="yyyy年MM月dd日")
	private Date birthday;
	private String academy;
	private String major;
	
	public StudentInfo(String studentNumber, String studentName, char gender, Date birthday, String academy,
					   String major) {
		super();
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.gender = gender;
		this.birthday = birthday;
		this.academy = academy;
		this.major = major;
	}

	public StudentInfo() {

	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

}