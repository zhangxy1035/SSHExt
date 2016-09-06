package org.ext.entity;

import java.io.Serializable;

public class Dep implements Serializable {
	private Integer id;
	private String depName;
	private String depTel;
	private School school;
	private int display;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepTel() {
		return depTel;
	}

	public void setDepTel(String depTel) {
		this.depTel = depTel;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}
}
