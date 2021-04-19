package com.godeltech.javamastery.testapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;

import com.godeltech.javamastery.testapp.dao.DepartmentDao;
import com.godeltech.javamastery.testapp.entity.Department;
import com.godeltech.javamastery.testapp.exception.EntityAlreadyExistException;
import com.godeltech.javamastery.testapp.mapper.DepartmentDtoMapper;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceImplTest {
	@Mock
	private DepartmentDao departmentDao;

	private DepartmentServiceImpl departmentServiceImpl;

	private DepartmentDtoMapper departmentDtoMapper;

	private Department department;

	private List<Department> departments;

	@Before
	public void init() {
		department = new Department(1l, "sales");
		departments = new ArrayList<Department>();
		departments.add(new Department(1l, "sales"));
		departments.add(new Department(2l, "programmers"));
		departments.add(new Department(3l, "managers"));
		departmentDtoMapper = new DepartmentDtoMapper();
		departmentServiceImpl = new DepartmentServiceImpl(departmentDao, departmentDtoMapper);
	}

	@Test
	public void updateTest() {
		when(departmentDao.get(any(Long.class))).thenReturn(department);
		Department departmentForUpdate = new Department(1l, "programmers");
		doAnswer((department) -> {
			Department dep = department.getArgument(0);
			System.out.println("called for id: " + dep.getId() + " and name: " + dep.getName());
			assertEquals(1L, dep.getId().longValue());
			assertEquals("programmers", dep.getName());
			return null;
		}).when(departmentDao).update(departmentForUpdate);
		departmentServiceImpl.update(departmentDtoMapper.toDepartmentDto(departmentForUpdate));
		verify(departmentDao, times(1)).get(departmentForUpdate.getId());
		verify(departmentDao, times(1)).update(departmentForUpdate);
	}

	@Test
	public void getByIdTest() {
		when(departmentDao.get(any(Long.class))).thenReturn(department);
		Department departmentForSave = departmentDtoMapper.toDepartment(departmentServiceImpl.getById(300l));
		assertThat(departmentForSave.getName()).isEqualTo(department.getName());
	}

	@Test
	public void createTest() {
		Department departmentForCreate = new Department(1l, "programmers");
		doAnswer((department) -> {
			Department dep = department.getArgument(0);
			System.out.println("called for id: " + dep.getId() + " and name: " + dep.getName());
			assertEquals(1L, dep.getId().longValue());
			assertEquals("programmers", dep.getName());
			return null;
		}).when(departmentDao).save(departmentForCreate);
		departmentServiceImpl.create(departmentDtoMapper.toDepartmentDto(departmentForCreate));
		verify(departmentDao, times(1)).save(departmentForCreate);
	}

	@Test
	public void whenthenEntityAlreadyExistExceptionThrown_thenAssertionSucceeds() {
		Department departmentForCreate = new Department(1l, "programmers");
		doAnswer((department) -> {
			throw new DuplicateKeyException("department with id already exist");
		}).when(departmentDao).save(departmentForCreate);
		Throwable thrown = assertThrows(EntityAlreadyExistException.class, () -> {
			departmentServiceImpl.create(departmentDtoMapper.toDepartmentDto(departmentForCreate));
		});
		assertNotNull(thrown.getMessage());
	}

	@Test
	public void deleteTest() {
		doAnswer((t) -> {
			return null;
		}).when(departmentDao).delete(anyLong());
		departmentServiceImpl.delete(1l);
		verify(departmentDao, times(1)).delete(1l);
	}

	@Test
	public void getAllTest() {
		when(departmentDao.getAll()).thenReturn(departments);
		List<Department> list = departmentDao.getAll();
		assertEquals(departments, list);
	}
}
