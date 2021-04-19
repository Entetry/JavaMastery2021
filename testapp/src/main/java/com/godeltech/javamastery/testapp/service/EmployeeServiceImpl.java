package com.godeltech.javamastery.testapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.godeltech.javamastery.testapp.dao.Dao;
import com.godeltech.javamastery.testapp.dto.EmployeeDto;
import com.godeltech.javamastery.testapp.entity.Department;
import com.godeltech.javamastery.testapp.entity.Employee;
import com.godeltech.javamastery.testapp.exception.EntityAlreadyExistException;
import com.godeltech.javamastery.testapp.mapper.EmployeeDtoMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	private final Dao<Employee> dao;
	private final Dao<Department> departmentDao;
	private final EmployeeDtoMapper employeeDtoMapper;

	@Autowired
	public EmployeeServiceImpl(Dao<Employee> dao, EmployeeDtoMapper employeeDtoMapper, Dao<Department> departmentDao) {
		this.dao = dao;
		this.employeeDtoMapper = employeeDtoMapper;
		this.departmentDao = departmentDao;
	}

	public void create(EmployeeDto employeeDto) throws EntityAlreadyExistException {
		departmentDao.get(employeeDto.getDepartment().getId());
		Employee employee = employeeDtoMapper.toEmployee(employeeDto);
		try {
			dao.save(employee);
		} catch (DuplicateKeyException ex) {
			LOGGER.error("Failed to create employee", ex);
			throw new EntityAlreadyExistException(Employee.class, "id", String.valueOf(employeeDto.getId()));
		} catch (Exception e) {
			LOGGER.error("Failed to create employee", e);
			throw e;
		}
	}

	public void delete(Long id) {
		try {
			dao.delete(id);
		} catch (Exception e) {
			LOGGER.error("could not delete employee!", e);
			throw e;
		}
	}

	public void update(EmployeeDto employeeDto) {
		departmentDao.get(employeeDto.getDepartment().getId());
		Employee employee = dao.get(employeeDto.getId());
		Employee employee2 = employeeDtoMapper.toEmployee(employeeDto);
		try {
			dao.update(employee2);
		} catch (Exception e) {
			LOGGER.error("could not update employee!!", e);
			throw e;
		}
	}

	public EmployeeDto getById(Long id) {
		return employeeDtoMapper.toEmployeeDto(dao.get(id));
	}

	public List<EmployeeDto> getAll() {
		return dao.getAll().stream().map(employeeDtoMapper::toEmployeeDto).collect(Collectors.toList());
	}

}
