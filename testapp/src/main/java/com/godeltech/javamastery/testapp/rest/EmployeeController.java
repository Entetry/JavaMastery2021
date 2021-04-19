package com.godeltech.javamastery.testapp.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.godeltech.javamastery.testapp.dto.EmployeeDto;
import com.godeltech.javamastery.testapp.service.EmployeeService;
import com.godeltech.javamastery.testapp.service.EmployeeServiceImpl;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	private final EmployeeService employeeService;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	public EmployeeController(EmployeeServiceImpl employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping
	public void create(@Valid @RequestBody EmployeeDto employeeDto) {
		try {
			employeeService.create(employeeDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping("/{id}")
	public EmployeeDto getById(@PathVariable Long id) {
		try {
			return employeeService.getById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		try {
			employeeService.delete(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@PutMapping
	public void update(@Valid @RequestBody EmployeeDto employeeDto) {
		try {
			employeeService.update(employeeDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping
	public List<EmployeeDto> getAll() {
		return employeeService.getAll();
	}
}
