package controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDAO;
import factory.ConnectionFactory;
import model.Product;

public class ProductController {
	
	private ProductDAO productDAO; 
	
	public ProductController() {
		
		Connection con = new ConnectionFactory().getConnection();
		
		this. productDAO = new ProductDAO(con);	
		
	}
	
	public void delete(Integer id) {
		this.productDAO.delete(id);
	}

	public void save(Product prod) {
		this.productDAO.save(prod);
	}

	public List<Product> list() {
		
		List<Product> produtos = new ArrayList<Product>();
		
		produtos =  this.productDAO.list();
		
		return produtos;
	}

	
	public void alter(String name, String description, Integer id) {
		
		this.productDAO.alter(name, description, id);
		
	}

	


}
