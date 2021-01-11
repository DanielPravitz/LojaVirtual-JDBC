package controller;

import java.sql.Connection;
import java.util.List;

import dao.CategoryDAO;
import factory.ConnectionFactory;
import model.Category;


public class CategoryController {
	
	private CategoryDAO CategoryDAO;

	public CategoryController() {
		
		Connection con = new ConnectionFactory().getConnection();
		
		this.CategoryDAO = new CategoryDAO(con);	
		
	}
	
	public List<Category> list(){
		
		return this.CategoryDAO.list();
	}
	
	
	


}
