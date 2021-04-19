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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;

import com.godeltech.javamastery.testapp.dao.DepartmentDao;
import com.godeltech.javamastery.testapp.dao.EmployeeDao;
import com.godeltech.javamastery.testapp.entity.Department;
import com.godeltech.javamastery.testapp.entity.Employee;
import com.godeltech.javamastery.testapp.exception.EntityAlreadyExistException;
import com.godeltech.javamastery.testapp.mapper.DepartmentDtoMapper;
import com.godeltech.javamastery.testapp.mapper.EmployeeDtoMapper;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

	@Mock
	private DepartmentDao departmentDao;

	@Mock
	private EmployeeDao employeeDao;

	private EmployeeServiceImpl employeeServiceImpl;

	private EmployeeDtoMapper employeeDtoMapper;

	private DepartmentDtoMapper departmentDtoMapper;

	private Department department;

	private Employee employee;

	private List<Employee> employees;

	@Before
	public void init() {
		department = new Department(1l, "programmers");
		employee = new Employee(1l, "anton", "klintsevich", department, "team lead", "male", new Date(1999, 6, 28));
		employees = new ArrayList<Employee>();
		employees.add(new Employee(1l, "anton", "klintsevich", department, "team lead", "male", new Date(1999, 6, 28)));
		employees.add(new Employee(2l, "egor", "egorov", department, "C# dev", "male", new Date(1999, 6, 28)));
		employees.add(new Employee(3l, "ivan", "ivanod", department, "JS dev", "non-binary", new Date(1999, 6, 28)));
		departmentDtoMapper = new DepartmentDtoMapper();
		employeeDtoMapper = new EmployeeDtoMapper(departmentDtoMapper);
		employeeServiceImpl = new EmployeeServiceImpl(employeeDao, employeeDtoMapper, departmentDao);
	}

	@Test
	public void updateTest() {
		when(departmentDao.get(any(Long.class))).thenReturn(department);
		when(employeeDao.get(anyLong())).thenReturn(employee);

		Employee employeeForUpdate = new Employee(1l, "anton", "klintsevich", department, "team lead", "male",
				new Date(1999, 6, 28));
		doAnswer((employee) -> {
			Employee emp = employee.getArgument(0);
			System.out.println("called for id: " + emp.getId() + "  First name: " + emp.getFirstname() + " Last Name "
					+ emp.getLastName() + "  Department id" + emp.getDepartment().getId() + "  Department name "
					+ emp.getDepartment().getName() + " job title  " + emp.getJobTitle() + "  Date of birth : "
					+ emp.getDateOfBirth());
			assertEquals(employeeForUpdate, emp);
			return null;
		}).when(employeeDao).update(employeeForUpdate);
		employeeServiceImpl.update(employeeDtoMapper.toEmployeeDto(employeeForUpdate));
		verify(departmentDao, times(1)).get(employeeForUpdate.getDepartment().getId());
		verify(employeeDao, times(1)).get(employeeForUpdate.getId());
		verify(employeeDao, times(1)).update(employeeForUpdate);
	}

	@Test
	public void getByIdTest() {
		when(employeeDao.get(any(Long.class))).thenReturn(employee);
		Employee receivedEmployee = employeeDtoMapper.toEmployee(employeeServiceImpl.getById(Long.valueOf(300l)));
		assertThat(receivedEmployee).isEqualTo(employee);
	}

	@Test
	public void createTest() {
		when(departmentDao.get(any(Long.class))).thenReturn(department);
		Employee employeeForCreate = new Employee(1l, "anton", "klintsevich", department, "team lead", "male",
				new Date(1999, 6, 28));
		doAnswer((employee) -> {
			Employee emp = employee.getArgument(0);
			System.out.println("called for id: " + emp.getId() + "  First name: " + emp.getFirstname() + " Last Name "
					+ emp.getLastName() + "  Department id" + emp.getDepartment().getId() + "  Department name "
					+ emp.getDepartment().getName() + " job title  " + emp.getJobTitle() + "  Date of birth : "
					+ emp.getDateOfBirth());
			assertEquals(employeeForCreate, emp);
			return null;
		}).when(employeeDao).save(employeeForCreate);
		employeeServiceImpl.create(employeeDtoMapper.toEmployeeDto(employeeForCreate));
		verify(departmentDao, times(1)).get(employeeForCreate.getDepartment().getId());
		verify(employeeDao, times(1)).save(employeeForCreate);
	}

	@Test
	public void whenthenEntityAlreadyExistExceptionThrown_thenAssertionSucceeds() {
		when(departmentDao.get(any(Long.class))).thenReturn(department);
		Employee employeeForCreate = new Employee(1l, "anton", "klintsevich", department, "team lead", "male",
				new Date(1999, 6, 28));
		doAnswer((employee) -> {
			throw new DuplicateKeyException("employee with id already exist");
		}).when(employeeDao).save(employeeForCreate);
		Throwable thrown = assertThrows(EntityAlreadyExistException.class, () -> {
			employeeServiceImpl.create(employeeDtoMapper.toEmployeeDto(employeeForCreate));
		});
		assertNotNull(thrown.getMessage());
	}

	@Test
	public void deleteTest() {
		doAnswer((t) -> {
			return null;
		}).when(employeeDao).delete(anyLong());
		employeeServiceImpl.delete(1l);
		verify(employeeDao, times(1)).delete(1l);
	}

	@Test
	public void getAllTest() {
		when(employeeDao.getAll()).thenReturn(employees);
		List<Employee> list = employeeDao.getAll();
		assertEquals(employees, list);
	}

}
