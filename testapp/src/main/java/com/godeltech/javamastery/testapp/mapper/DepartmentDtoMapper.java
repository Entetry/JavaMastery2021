package com.godeltech.javamastery.testapp.mapper;

import org.springframework.stereotype.Component;

import com.godeltech.javamastery.testapp.dto.DepartmentDto;
import com.godeltech.javamastery.testapp.entity.Department;

@Component
public class DepartmentDtoMapper {
	public Department toDepartment(DepartmentDto departmentDto) {
		Department department = new Department();
		if(departmentDto.getId()!=null) {
		department.setId(departmentDto.getId());
		}
		department.setName(departmentDto.getName());
		return department;
	}
	public DepartmentDto toDepartmentDto(Department department) {
		DepartmentDto departmentDto = new DepartmentDto();
		if(department.getId()!=null) {
			departmentDto.setId(department.getId());
		}
		departmentDto.setName(department.getName());
		return departmentDto;
	}
}
