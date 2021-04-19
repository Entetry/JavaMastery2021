package com.godeltech.javamastery.testapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.godeltech.javamastery.testapp.entity.Employee;
import com.godeltech.javamastery.testapp.exception.EntityNotFoundException;
import com.godeltech.javamastery.testapp.mapper.EmployeeRowMapper;

@Repository
public class EmployeeDao extends AbstractDAO<Employee> {

	private static final String TABLE_NAME = "EMPLOYEE";
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);
	private final EmployeeRowMapper rowMapper;
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM " + TABLE_NAME
			+ " JOIN DEPARTMENT ON employee.department_id = department.id WHERE employee.employee_id = ?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME
			+ " JOIN DEPARTMENT ON employee.department_id = department.id";
	private static final String UPDATE_QUERY = "update " + TABLE_NAME
			+ " set first_name = ?,last_name = ?, department_id = ?,job_title = ?, gender = ?,date_of_birth = ? where employee_id = ?";;
	private static final String DELETE_QUERY = "delete from " + TABLE_NAME + " where employee_id = ?";

	@Autowired
	public EmployeeDao(DataSource dataSource, EmployeeRowMapper rowMapper) {
		super(dataSource, TABLE_NAME, rowMapper);
		this.rowMapper = rowMapper;
	}

	@Override
	public Employee get(long id) throws EntityNotFoundException {
		try {
			return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new Object[] { id }, rowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOGGER.error("Entity not found", ex);
			throw new EntityNotFoundException(Employee.class, "id", String.valueOf(id));
		}
	}

	@Override
	public List<Employee> getAll() {
		return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper);
	}

	@Override
	public void save(Employee t) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("employee_id", t.getId());
		parameters.put("first_name", t.getFirstname());
		parameters.put("last_name", t.getLastName());
		parameters.put("department_id", t.getDepartment().getId());
		parameters.put("job_title", t.getJobTitle());
		parameters.put("gender", t.getGender());
		parameters.put("date_of_birth", t.getDateOfBirth());
		simpleJdbcInsert.execute(parameters);

	}

	@Override
	public void update(Employee t) {
		jdbcTemplate.update(UPDATE_QUERY, t.getFirstname(), t.getLastName(), t.getDepartment().getId(), t.getJobTitle(),
				t.getGender(), t.getDateOfBirth(), t.getId());
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update(DELETE_QUERY, id);
	}

}
