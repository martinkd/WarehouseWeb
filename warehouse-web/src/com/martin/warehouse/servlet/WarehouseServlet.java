package com.martin.warehouse.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.martin.warehouse.dao.WarehouseItemDao;
import com.martin.warehouse.entity.Item;
import com.martin.warehouse.entity.WarehouseItem;

/**
 * Servlet implementation class WarehouseServlet
 */
@WebServlet("/warehouse")
public class WarehouseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String OTHER_SUPPLIER_ID = "otherSup123";
	private static final double DEFAULT_PROFIT_RATE = 0.20;
	private WarehouseItemDao wdao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WarehouseServlet() {
		
	}

	@Override
	public void init() throws ServletException {
		wdao = new WarehouseItemDao();
		Item item = new Item("test", 10, 500, OTHER_SUPPLIER_ID);
		WarehouseItem item1 = new WarehouseItem(item);
		item1.setProfitRate(DEFAULT_PROFIT_RATE);
		wdao.add(item1);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<WarehouseItem> items;
		String name = request.getParameter("searchName");
		if (name != null && !name.trim().isEmpty()) {
			items = findByName(name);
		} else {
			items = getAllItems(request);
		}
		String jsonItems = new Gson().toJson(items);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonItems);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		add(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		update(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		delete(request);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String quantityStr = request.getParameter("quantity");
		String priceStr = request.getParameter("price");

		if (validate(name, quantityStr, priceStr)) {
			try {
				int quantity = Integer.parseInt(quantityStr);
				double price = Double.parseDouble(priceStr);
				Item itemToAdd = new Item(name, quantity, price, OTHER_SUPPLIER_ID);
				WarehouseItem item = new WarehouseItem(itemToAdd);
				item.setProfitRate(DEFAULT_PROFIT_RATE);
				wdao.add(item);
			} catch (NumberFormatException e) {
				response.getWriter().write(e.getMessage());
			}
		} else {
			response.getWriter().write("there is empty field(s)");
		}
	}

	private boolean validate(String name, String quantityStr, String priceStr) {
		return name != null && !name.trim().isEmpty() && quantityStr != null && !quantityStr.trim().isEmpty()
				&& priceStr != null && !priceStr.trim().isEmpty();
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		String name = request.getParameter("name");
		String quantityStr = request.getParameter("quantity");
		String priceStr = request.getParameter("price");

		if (idStr != null && !idStr.trim().isEmpty() && validate(name, quantityStr, priceStr)) {
			try {
				long id = Long.parseLong(idStr);
				int quantity = Integer.parseInt(quantityStr);
				double price = Double.parseDouble(priceStr);
				WarehouseItem item = wdao.getById(id);
				item.setName(name);
				item.setQuantity(quantity);
				item.setPrice(price);
				wdao.update(item);
			} catch (NumberFormatException e) {
				response.getWriter().write(e.getMessage());
			}
		}
	}

	private void delete(HttpServletRequest request) {
		String idStr = request.getParameter("toRemove");
		if (idStr != null && !idStr.trim().isEmpty()) {
			try {
				long id = Long.parseLong(idStr);
				wdao.remove(id);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		}
	}

	private List<WarehouseItem> getAllItems(HttpServletRequest request) {
		return wdao.getAllItems();
	}

	private List<WarehouseItem> findByName(String name) {
		return wdao.findByName(name);
	}
	
	private String getSupplierId(HttpServletRequest request) {
		return request.getUserPrincipal().getName();
	}
}
