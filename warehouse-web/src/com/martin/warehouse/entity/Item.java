package com.martin.warehouse.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="allItems", query="SELECT i FROM Item i")
public class Item extends ItemBase {

	public Item() {};
	
	public Item(String name, int quantity, double price, String supplierId) {
		super(name, quantity, price, supplierId);
	}
}
