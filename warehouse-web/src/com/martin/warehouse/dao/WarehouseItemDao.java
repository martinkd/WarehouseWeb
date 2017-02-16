package com.martin.warehouse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.martin.warehouse.entity.Item;
import com.martin.warehouse.entity.WarehouseItem;

public class WarehouseItemDao {

	private EntityManager em;

	public WarehouseItemDao() {
		em = EntityManagerProvider.getInstance().getEntityManager();
	}

	public void add(WarehouseItem item) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(item);
		tx.commit();
	}

	public WarehouseItem getById(long id) {
		return em.find(WarehouseItem.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<WarehouseItem> findByName(String name) {
		Query query = em.createQuery("SELECT i FROM WarehouseItem i WHERE i.name LIKE :name", WarehouseItem.class);
		query.setParameter("name", "%" + name + "%");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> findBySupplier(String supplierId) {
		Query query = em.createQuery("SELECT i FROM WarehouseItem i WHERE  i.supplierId = :supplierId", WarehouseItem.class);
		query.setParameter("supplierId", supplierId);
		return query.getResultList();
	}
	
	public void update(WarehouseItem item) {
		EntityTransaction tx = em.getTransaction();
		WarehouseItem found = getById(item.getId());
		tx.begin();
		found.setName(item.getName());
		found.setPrice(item.getPrice());
		found.setQuantity(item.getQuantity());
		found.setProfitRate(item.getProfitRate());
		tx.commit();
	}
	
	public void remove(long id) {
		EntityTransaction tx = em.getTransaction();
		WarehouseItem found = getById(id);
		tx.begin();
		em.remove(found);
		tx.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<WarehouseItem> getAllItems() {
		return em.createNamedQuery("allWarehouseItems").getResultList();
	}
}
