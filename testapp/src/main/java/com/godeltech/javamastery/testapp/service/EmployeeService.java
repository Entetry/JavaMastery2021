package com.godeltech.javamastery.testapp.service;

import java.util.List;

import com.godeltech.javamastery.testapp.dto.EmployeeDto;

public interface EmployeeService {

	void create(EmployeeDto employeeDto);

	void delete(Long id);

	void update(EmployeeDto employeeDto);

	EmployeeDto getById(Long id);

	List<EmployeeDto> getAll();
}
