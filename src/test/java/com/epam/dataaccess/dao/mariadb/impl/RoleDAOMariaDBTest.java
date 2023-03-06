package com.epam.dataaccess.dao.mariadb.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.dataaccess.dao.mariadb.datasource.QueryBuilder;
import com.epam.dataaccess.entity.Role;
import com.epam.exception.dao.DAOReadException;

class RoleDAOMariaDBTest {

	@Mock
	private QueryBuilder mockQueryBuilder;

	@Mock
	private ResultSet mockResultSet;

	private RoleDAOMariaDB roleDAO;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		roleDAO = new RoleDAOMariaDB() {
			@Override
			protected QueryBuilder getQueryBuilder() throws SQLException {
				return mockQueryBuilder;
			}
		};
	}

	@Test
	void testGetRole() throws Exception {
		Role expectedRole = new Role(1, "ADMIN", "Admin role");
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_ROLE_BY_ID)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(1)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.ROLE_ID_FIELD)).thenReturn(1);
		when(mockResultSet.getString(MariaDBConstants.ROLE_NAME_FIELD)).thenReturn("ADMIN");
		when(mockResultSet.getString(MariaDBConstants.ROLE_DESCRIPTION_FIELD)).thenReturn("Admin role");

		Role role = roleDAO.get(1);

		assertEquals(expectedRole, role);
	}

	@Test
	void testGetAllRoles() throws Exception {
		List<Role> expectedRoles = new ArrayList<Role>();
		expectedRoles.add(new Role(1, "ADMIN", "Admin role"));
		expectedRoles.add(new Role(2, "USER", "User role"));
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_ALL_ROLES)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.ROLE_ID_FIELD)).thenReturn(1).thenReturn(2);
		when(mockResultSet.getString(MariaDBConstants.ROLE_NAME_FIELD)).thenReturn("ADMIN").thenReturn("USER");
		when(mockResultSet.getString(MariaDBConstants.ROLE_DESCRIPTION_FIELD)).thenReturn("Admin role")
				.thenReturn("User role");
		List<Role> roles = roleDAO.getAll();
		assertEquals(expectedRoles, roles);
	}

	@Test
	void testInsertRole() throws Exception {
		Role expectedRole = new Role(3, "TESTER", "Tester role");
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.ADD_ROLE)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField("TESTER")).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField("Tester role")).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int updated = roleDAO.insert(expectedRole);

		assertEquals(1, updated);
	}

	@Test
	void testUpdateRole() throws Exception {
		Role expectedRole = new Role(3, "UPDATED_TESTER", "Updated Tester role");
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.UPDATE_ROLE)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField("UPDATED_TESTER")).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField("Updated Tester role")).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(3)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int updated = roleDAO.update(expectedRole);
		assertEquals(1, updated);
	}

	@Test
	void testDeleteRole() throws Exception {
		Role deleteRole = new Role(1, "UPDATED_TESTER", "Updated Tester role");
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.DELETE_ROLE)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(1)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		int deleted = roleDAO.delete(deleteRole);
		assertEquals(1, deleted);
	}
	
	@Test
	void testGetSQLExceptions() throws SQLException{
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_ROLE_BY_ID)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(1)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenThrow(SQLException.class);
		assertThrows(DAOReadException.class, () -> {roleDAO.get(1);});
		
	}

}