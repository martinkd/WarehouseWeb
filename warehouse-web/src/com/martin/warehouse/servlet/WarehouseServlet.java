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
import com.martin.warehouse.dao.WarehouseItemDao;
import com.martin.warehouse.entity.Item;
import com.martin.warehouse.entity.WarehouseItem;

/**
 * Servlet implementation class WarehouseServlet
 */
@WebServlet("/warehouse")
public class WarehouseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WarehouseItemDao widao;
	private ItemDao idao;

	public WarehouseServlet() {
		widao = new WarehouseItemDao();
		idao = new ItemDao();
	}

	@Override
	public void init() throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<WarehouseItem> items;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		order(request, response);
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
				WarehouseItem item = widao.getById(id);
				item.setName(name);
				item.setQuantity(quantity);
				item.setPrice(price);
				idao.update(item);
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
				idao.remove(id);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void order(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		if (idStr != null && !idStr.trim().isEmpty() && quantityStr != null && !quantityStr.trim().isEmpty()) {
			try {
				long id = Long.parseLong(idStr);
				int quantity = Integer.parseInt(quantityStr);
				Item item = idao.getById(id);
				if (quantity != 0 && quantity <= item.getQuantity()) {
					WarehouseItem wItem = new WarehouseItem(item);
					wItem.setQuantity(quantity);
				} else {
					response.getWriter().write("not enough quantity");
				}
			} catch (NumberFormatException e) {
				response.getWriter().write(e.getMessage());
			}
		}
	}

	private List<WarehouseItem> getAllItems() {
		return widao.getAllItems();
	}

	private List<WarehouseItem> findByName(String name) {
		return widao.findByName(name);
	}

	private String getSupplierId(HttpServletRequest request) {
		return request.getUserPrincipal().getName();
	}

}
