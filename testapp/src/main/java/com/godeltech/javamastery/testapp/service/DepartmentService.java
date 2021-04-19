package com.godeltech.javamastery.testapp.service;

import java.util.List;

import com.godeltech.javamastery.testapp.dto.DepartmentDto;

public interface DepartmentService {

	void create(DepartmentDto departmentDto);

	void delete(Long id);

	void update(DepartmentDto departmentDto);

	DepartmentDto getById(Long id);

	List<DepartmentDto> getAll();
}
