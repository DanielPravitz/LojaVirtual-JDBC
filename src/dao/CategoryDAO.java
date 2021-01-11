package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.Product;

public class CategoryDAO {

	private Connection connection;
	
	public CategoryDAO(Connection connection) {
		this.connection = connection;
		
	}
	
	public List<Category> list() {
		try {
			List<Category> categories = new ArrayList<Category>();
			
			String sql = "SELECT ID, NOME FROM CATEGORIA";
			
			try(PreparedStatement stm = connection.prepareStatement(sql)){
				
				stm.execute();
				
				try(ResultSet rst = stm.getResultSet()){
					
					while (rst.next()) {
						
						Category category = new Category(rst.getInt(1), rst.getString(2));
						
						categories.add(category);
						
					}

				//auto close result set	
				}
				
			//auto close prepared statement	
			}
			return categories;
			
		}
		catch(SQLException e) {
			
			throw new RuntimeException(e);
		}
		
		
		
		

	}
	
	public List<Category> listWithProduct() throws SQLException {
	
		Category lastOne = null;
		
		List<Category> categories = new ArrayList<Category>();
		
		String sql = "SELECT C.ID, C.NOME, P.ID, P.NOME, P.DESCRICAO  FROM CATEGORIA C INNER JOIN PRODUTO P ON C.ID = P.CATEGORIA_ID";
		
		try(PreparedStatement stm = connection.prepareStatement(sql)){
			
			stm.execute();
			
			try(ResultSet rst = stm.getResultSet()){
				
				while (rst.next()) {
					
					if(lastOne == null || !lastOne.getName().equals( rst.getString(2))) {
						
						Category category = new Category(rst.getInt(1), rst.getString(2));
						lastOne = category;
						categories.add(category);
					
					}
					
					Product prod = new Product (rst.getInt(3), rst.getString(4), rst.getString(5));
					
					lastOne.addProduct(prod);
				}

			//auto close result set	
			}
			
		//auto close prepared statement	
		}
		
		return categories;
	}
		
}
