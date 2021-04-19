package com.godeltech.javamastery.testapp.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.godeltech.javamastery.testapp.entity.Department;
import com.godeltech.javamastery.testapp.exception.EntityNotFoundException;
import com.godeltech.javamastery.testapp.mapper.DepartmentRowMapper;

@Repository
public class DepartmentDao extends AbstractDAO<Department> {
	private static final String TABLE_NAME = "DEPARTMENT";
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDao.class);
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
	private static final String UPDATE_QUERY = "update " + TABLE_NAME + " set name = ? where id = ?";
	private static final String DELETE_QUERY = "delete from " + TABLE_NAME + " where id = ?";
	private final DepartmentRowMapper departmentRowMapper;

	@Autowired
	public DepartmentDao(DataSource dataSource, DepartmentRowMapper departmentRowMapper) {
		super(dataSource, TABLE_NAME, departmentRowMapper);
		this.departmentRowMapper = departmentRowMapper;
	}

	@Override
	public Department get(long id) throws EntityNotFoundException{
		try {
			return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new Object[] { id }, departmentRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOGGER.error("Entity not found", ex);
			throw new EntityNotFoundException(Department.class, "id", String.valueOf(id));
		}

	}

	@Override
	public void save(Department t) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", t.getId());
		parameters.put("name", t.getName());
		simpleJdbcInsert.execute(parameters);
	}

	@Override
	public void update(Department t) {
		jdbcTemplate.update(UPDATE_QUERY, t.getName(), t.getId());
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update(DELETE_QUERY, id);
	}

}
