package com.godeltech.javamastery.testapp.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.godeltech.javamastery.testapp.dto.DepartmentDto;
import com.godeltech.javamastery.testapp.service.DepartmentService;
import com.godeltech.javamastery.testapp.service.DepartmentServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/departments")
public class DepartmentController {
	private final DepartmentService departmentService;
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	public DepartmentController(DepartmentServiceImpl departmentService) {
		this.departmentService = departmentService;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void create(@Valid @RequestBody DepartmentDto departmentDto) {
		try {
			departmentService.create(departmentDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		try {
			departmentService.delete(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@PutMapping
	public void update(@Valid @RequestBody DepartmentDto departmentDto) {
		try {
			departmentService.update(departmentDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping("/{id}")
	public DepartmentDto getById(@PathVariable Long id) {
		return departmentService.getById(id);
	}

	@GetMapping
	public List<DepartmentDto> getAll() {
		return departmentService.getAll();
	}
}
