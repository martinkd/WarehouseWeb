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
		String name = request.getParameter("name");
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

	}

	private List<Item> getAllItems() {
		return idao.getAllItems();
	}

	private List<Item> findByName(String name) {
		return idao.findAllItemsByName(name);
	}

}