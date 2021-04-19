package com.godeltech.javamastery.testapp.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class AbstractDAO<T> implements Dao<T> {

	protected DataSource dataSource;

	protected JdbcTemplate jdbcTemplate;

	protected SimpleJdbcInsert simpleJdbcInsert;

	protected SimpleJdbcCall simpleJdbcCall;

	protected RowMapper<T> rowMapper;

	protected String tableName;

	public AbstractDAO(DataSource dataSource, String tableName, RowMapper<T> rowMapper) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(tableName);
		this.rowMapper = rowMapper;
		this.tableName = tableName;
	}

	@Override
	public List<T> getAll() {
		return jdbcTemplate.query("SELECT * FROM " + tableName, rowMapper);
	}

}
