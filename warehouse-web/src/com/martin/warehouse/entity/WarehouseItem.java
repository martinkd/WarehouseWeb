package com.martin.warehouse.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "allWarehouseItems", query = "SELECT i FROM WarehouseItem i")
public class WarehouseItem extends ItemBase {

	private Double profitRate;

	public WarehouseItem() {
	}
	
	public WarehouseItem(Item item) {
		super(item);
	}

	public double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(double profitRate) {
		this.profitRate = profitRate;
	}

	private double calculatePrice(double oldPrice, double profitRate) {
		return oldPrice += oldPrice * profitRate / 100;

	}

	@Override
	public String toString() {
		return String.format("WarehouseItem [id=%d, name=%s, quantity=%d, price=%.2f, supplierId=%d]", getId(),
				getName(), getQuantity(), calculatePrice(getPrice(), getProfitRate()), getSupplierId());
	}
}
