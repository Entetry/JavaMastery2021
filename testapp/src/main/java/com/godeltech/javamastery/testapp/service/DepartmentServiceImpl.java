package com.godeltech.javamastery.testapp.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.godeltech.javamastery.testapp.dao.Dao;
import com.godeltech.javamastery.testapp.dto.DepartmentDto;
import com.godeltech.javamastery.testapp.entity.Department;
import com.godeltech.javamastery.testapp.exception.EntityAlreadyExistException;
import com.godeltech.javamastery.testapp.mapper.DepartmentDtoMapper;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);
	private final Dao<Department> dao;
	private final DepartmentDtoMapper departmentDtoMapper;

	@Autowired
	public DepartmentServiceImpl(Dao<Department> dao, DepartmentDtoMapper departmentDtoMapper) {
		this.dao = dao;
		this.departmentDtoMapper = departmentDtoMapper;
	}

	public void create(DepartmentDto departmentDto) throws EntityAlreadyExistException {
		Department department = departmentDtoMapper.toDepartment(departmentDto);
		try {
			dao.save(department);
		} catch (DuplicateKeyException ex) {
			LOGGER.error("Failed to create department", ex);
			throw new EntityAlreadyExistException(Department.class, "id", String.valueOf(departmentDto.getId()));
		} catch (Exception e) {
			LOGGER.error("Failed to create department", e);
			throw e;
		}
	}

	public void delete(Long id) {
		try {
			dao.delete(id);
		} catch (Exception e) {
			LOGGER.error("could not delete department!", e);
			throw e;
		}
	}

	public void update(DepartmentDto departmentDto) {
		dao.get(departmentDto.getId());
		Department department2 = departmentDtoMapper.toDepartment(departmentDto);
		try {
			dao.update(department2);
		} catch (Exception e) {
			LOGGER.error("could not update department!", e);
			throw e;
		}
	}

	public DepartmentDto getById(Long id) {
		return departmentDtoMapper.toDepartmentDto(dao.get(id));
	}

	public List<DepartmentDto> getAll() {
		return StreamSupport.stream(dao.getAll().spliterator(), false).map(departmentDtoMapper::toDepartmentDto)
				.collect(Collectors.toList());
	}
}
