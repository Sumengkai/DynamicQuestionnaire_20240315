package com.example.Dynamic_Questionnaire.Entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "people")
public class People {
	@Id
	@Column(name = "people_id")
	@Type(type = "uuid-char")
	private UUID peopleUuid;

	@Column(name = "name")
	private String name;

	@Column(name = "man_or_girl")
	private String manOrGirl;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "age")
	private int age;

	public People() {
	}

	public People(UUID peopleUuid, String name, String manOrGirl, String email, String phone, int age) {
		this.peopleUuid = peopleUuid;
		this.name = name;
		this.manOrGirl = manOrGirl;
		this.email = email;
		this.phone = phone;
		this.age=age;
	}

	public UUID getPeopleUuid() {
		return peopleUuid;
	}

	public void setPeopleUuid(UUID peopleUuid) {
		this.peopleUuid = peopleUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManOrGirl() {
		return manOrGirl;
	}

	public void setManOrGirl(String manOrGirl) {
		this.manOrGirl = manOrGirl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
