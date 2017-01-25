package com.martin.warehouse.dao;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class EntityManagerProvider {

	private static EntityManagerProvider emp;
	private EntityManagerFactory emf;
	private EntityManager em;
	private DataSource ds;

	private EntityManagerProvider() {
		createEntityManager();
	}

	public static EntityManagerProvider getInstance() {
		if (emp == null) {
			emp = new EntityManagerProvider();
		}
		return emp;
	}

	private void createEntityManager() {
		createEmFactory();
		em = emf.createEntityManager();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createEmFactory() {
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
			Map properties = new HashMap();
			properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
			emf = Persistence.createEntityManagerFactory("warehouse-web", properties);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
}
