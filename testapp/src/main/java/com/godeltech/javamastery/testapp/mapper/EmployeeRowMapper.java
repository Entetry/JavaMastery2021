package com.godeltech.javamastery.testapp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.godeltech.javamastery.testapp.entity.Department;
import com.godeltech.javamastery.testapp.entity.Employee;

@Component
public class EmployeeRowMapper implements RowMapper<Employee> {
	private final RowMapper<Department> departmentRowMapper;

	@Autowired
	public EmployeeRowMapper(RowMapper<Department> departmentRowMapper) {
		this.departmentRowMapper = departmentRowMapper;
	}

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setId(rs.getLong(EmployeeConstants.ID));
		employee.setFirstName(rs.getString(EmployeeConstants.FIRSTNAME));
		employee.setLastname(rs.getString(EmployeeConstants.LASTNAME));
		employee.setDepartment(departmentRowMapper.mapRow(rs, rowNum));
		employee.setJobTitle(rs.getString(EmployeeConstants.JOB_TITLE));
		employee.setGender(rs.getString(EmployeeConstants.GENDER));
		employee.setDateOfBirth(rs.getDate(EmployeeConstants.DATE_OF_BIRTH));
		return employee;
	}
}