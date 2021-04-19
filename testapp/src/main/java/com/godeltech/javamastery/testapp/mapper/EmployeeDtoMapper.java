package com.godeltech.javamastery.testapp.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.godeltech.javamastery.testapp.dto.EmployeeDto;
import com.godeltech.javamastery.testapp.entity.Employee;

@Component
public class EmployeeDtoMapper {
	private final DepartmentDtoMapper departmentDtoMapper;
	
	
	@Autowired
	public EmployeeDtoMapper(DepartmentDtoMapper departmentDtoMapper) {
		this.departmentDtoMapper = departmentDtoMapper;
	}

	public Employee toEmployee(EmployeeDto employeeDto) {
		Employee employee = new Employee();
		employee.setId(employeeDto.getId());
		employee.setFirstName(employeeDto.getFirstName());
		employee.setLastname(employeeDto.getLastname());
		employee.setJobTitle(employeeDto.getJobTitle());
		employee.setGender(employeeDto.getGender());
		employee.setDateOfBirth(employeeDto.getDateOfBirth());
		employee.setDepartment(departmentDtoMapper.toDepartment(employeeDto.getDepartment()));
		return employee;
	}

	public EmployeeDto toEmployeeDto(Employee employee) {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(employee.getId());
		employeeDto.setFirstName(employee.getFirstname());
		employeeDto.setLastname(employee.getLastName());
		employeeDto.setJobTitle(employee.getJobTitle());
		employeeDto.setGender(employee.getGender());
		employeeDto.setDateOfBirth(employee.getDateOfBirth());
		employeeDto.setDepartment(departmentDtoMapper.toDepartmentDto(employee.getDepartment()));
		return employeeDto;
	}
}
