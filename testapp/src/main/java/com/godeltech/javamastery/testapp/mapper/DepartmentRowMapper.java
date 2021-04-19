package com.godeltech.javamastery.testapp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.godeltech.javamastery.testapp.entity.Department;

@Component
public class DepartmentRowMapper implements RowMapper<Department> {
	@Override
	public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
		Department department = new Department();
		department.setId(rs.getLong(DepartmentConstanst.ID));
		department.setName(rs.getString(DepartmentConstanst.NAME));
		return department;
	}
}