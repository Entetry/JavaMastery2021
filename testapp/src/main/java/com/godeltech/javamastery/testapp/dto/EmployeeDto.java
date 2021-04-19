package com.godeltech.javamastery.testapp.dto;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDto {
	@JsonProperty
	private Long id;
	@JsonProperty
	@NotBlank(message = "First name is mandatory")
	@Size(min = 2, max = 200, message = "Firstname be between 2 and 200 characters")
	private String firstName;
	@JsonProperty
	@NotBlank(message = "Last name is mandatory")
	@Size(min = 2, max = 254, message = "Lastname be between 2 and 254 characters")
	private String lastName;
	@JsonProperty
	private DepartmentDto department;
	@JsonProperty
	@NotBlank(message = "Firstname is mandatory")
	@Size(min = 2, max = 100, message = "JobTitle be between 2 and 100 characters")
	private String jobTitle;
	@JsonProperty
	@NotBlank
	@Size(min = 2, max = 200, message = "Gender be between 2 and 200 characters")
	private String gender;
	@JsonProperty
	private Date dateOfBirth;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastName;
	}

	public void setLastname(String lastName) {
		this.lastName = lastName;
	}

	public DepartmentDto getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDto department) {
		this.department = department;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
