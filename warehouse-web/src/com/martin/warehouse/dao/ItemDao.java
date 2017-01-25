package com.martin.warehouse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.martin.warehouse.entity.Item;

public class ItemDao {

	private EntityManager em;

	public ItemDao() {
		em = EntityManagerProvider.getInstance().getEntityManager();
	}

	public void add(Item item) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(item);
		tx.commit();
	}

	public Item getById(long id) {
		return em.find(Item.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Item> findByName(String name, long supplierId) {
		Query query = em.createQuery("SELECT i FROM Item i WHERE i.name LIKE :name AND i.supplierId = :supplierId",
				Item.class);
		query.setParameter("name", "%" + name + "%");
		query.setParameter("supplierId", supplierId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getAllItemsFromSupplier(long supplierId) {
		Query query = em.createQuery("SELECT i FROM Item i WHERE  i.supplierId = :supplierId", Item.class);
		query.setParameter("supplierId", supplierId);
		return query.getResultList();
	}

	public void update(Item item) {
		EntityTransaction tx = em.getTransaction();
		Item found = getById(item.getId());
		tx.begin();
		found.setName(item.getName());
		found.setPrice(item.getPrice());
		found.setQuantity(item.getQuantity());
		tx.commit();
	}

	public void remove(long id) {
		EntityTransaction tx = em.getTransaction();
		Item found = getById(id);
		tx.begin();
		em.remove(found);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Item> getAllItems() {
		return em.createNamedQuery("allItems").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findAllItemsByName(String name) {
		Query query = em.createQuery("SELECT i FROM Item i WHERE i.name LIKE :name", Item.class);
		query.setParameter("name", "%" + name + "%");
		return query.getResultList();
	}
}
