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
 * Servlet implementation class AdminServlet
 */
@WebServlet("/suppliers")
public class SuppliersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemDao idao;
	
    public SuppliersServlet() {
    }

    @Override
	public void init() throws ServletException {
		idao = new ItemDao();
	}

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


	private List<Item> getAllItems() {
		return idao.getAllItems();
	}

	private List<Item> findByName(String name) {
		return idao.findAllItemsByName(name);
	}
	
	private String getSupplierId(HttpServletRequest request) {
		return request.getUserPrincipal().getName();
	}

}
