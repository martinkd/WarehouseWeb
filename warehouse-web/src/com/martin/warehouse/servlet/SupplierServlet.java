package com.martin.warehouse.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.martin.warehouse.dao.ItemDao;
import com.martin.warehouse.entity.Item;

/**
 * Servlet implementation class SupplierServlet
 */
@WebServlet("/supplier")
public class SupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long DEFAULT_SUPPLIER_ID = 1L;
	private ItemDao idao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupplierServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		idao = new ItemDao();
		Item item1 = new Item("samsung", 1, 100, DEFAULT_SUPPLIER_ID);
		Item item2 = new Item("letv", 1, 100, DEFAULT_SUPPLIER_ID);
		Item item3 = new Item("iphone", 1, 100, DEFAULT_SUPPLIER_ID);
		idao.add(item1);
		idao.add(item2);
		idao.add(item3);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Item> items;
		String name = request.getParameter("searchName");
		if (name != null && !name.trim().isEmpty()) {
			items = findByName(name);
		} else {
			items = getAllItems();
		}
		String jsonItems = new Gson().toJson(items);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonItems);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		add(request);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		update(request);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		delete(request);
	}

	private void add(HttpServletRequest request) {
		String name = request.getParameter("name");
		String quantityStr = request.getParameter("quantity");
		String priceStr = request.getParameter("price");

		if (validate(name, quantityStr, priceStr)) {
			try {
				int quantity = Integer.parseInt(quantityStr);
				double price = Double.parseDouble(priceStr);
				Item item = new Item(name, quantity, price, DEFAULT_SUPPLIER_ID);
				idao.add(item);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean validate(String name, String quantityStr, String priceStr) {
		return name != null && !name.trim().isEmpty() && quantityStr != null && !quantityStr.trim().isEmpty()
				&& priceStr != null && !priceStr.trim().isEmpty();
	}

	private void update(HttpServletRequest request) {
		String idStr = request.getParameter("id");
		String name = request.getParameter("name");
		String quantityStr = request.getParameter("quantity");
		String priceStr = request.getParameter("price");

		if (idStr != null && !idStr.trim().isEmpty() && validate(name, quantityStr, priceStr)) {
			try {
				long id = Long.parseLong(idStr);
				int quantity = Integer.parseInt(quantityStr);
				double price = Double.parseDouble(priceStr);
				Item item = idao.getById(id);
				item.setName(name);
				item.setQuantity(quantity);
				item.setPrice(price);
				idao.update(item);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	private void delete(HttpServletRequest request) {
		String idStr = request.getParameter("toRemove");
		if (idStr != null && !idStr.trim().isEmpty()) {
			try {
				long id = Long.parseLong(idStr);
				idao.remove(id);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		}
	}

	private List<Item> getAllItems() {
		return idao.getAllItems();
	}

	private List<Item> findByName(String name) {
		return idao.findAllItemsByName(name);
	}

}
