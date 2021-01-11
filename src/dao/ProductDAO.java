package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.Product;

public class ProductDAO {
	
	private Connection connection;
	
	public ProductDAO(Connection connection) {
		this.connection = connection;
	}
	
	
	public void save(Product prod) {
		String sql = "INSERT INTO PRODUTO (nome, descricao) VALUES (?, ?)";
		try {
			try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				stm.setString(1, prod.getName());
				stm.setString(2, prod.getDescription());

				stm.execute();

				try (ResultSet rst = stm.getGeneratedKeys()) {

					while (rst.next()) {
						prod.setId(rst.getInt(1));
					}
			
				}
				
			//auto close preparedStatement
			}
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void saveWithCategory(Product prod) {
		
		String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO, CATEGORIA_ID) VALUES (?, ?, ?)";
		try {
			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, prod.getName());
				pstm.setString(2, prod.getDescription());
				pstm.setInt(3, prod.getCategoryId());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						prod.setId(rst.getInt(1));
					}
				}
			}
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public List<Product> list(){
		
		List<Product> products = new ArrayList<Product>();
		
		String sql = "SELECT ID, NOME, DESCRICAO FROM PRODUTO";
		
		try {

			try(PreparedStatement stm = connection.prepareStatement(sql)){
				
				stm.execute();
				
				try(ResultSet rst = stm.getResultSet()){
					
					while (rst.next()) {
						
						Product product = new Product(rst.getInt(1), rst.getString(2),rst.getString(3));
						
						products.add(product);
						
					}

				//auto close result set	
				}
				
			//auto close prepared statement	
			}
			
			return products;
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Product> search(Category ct){
		List<Product> products = new ArrayList<Product>();
		String sql = "SELECT ID, NOME, DESCRICAO FROM PRODUTO WHERE CATEGORIA_ID = ?";
		
		try {
			
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, ct.getId());
				pstm.execute();

				trasformResultSetToProduct( products, pstm);
			}
			return products;
		
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void delete(Integer id) {
		
		try {
			try (PreparedStatement stm = connection.prepareStatement("DELETE FROM PRODUTO WHERE ID = ?")) {
				stm.setInt(1, id);
				stm.execute();
			}
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	public void alter(String name, String description, Integer id) {
		
		try {
			try (PreparedStatement stm = connection
					.prepareStatement("UPDATE PRODUTO P SET P.NOME = ?, P.DESCRICAO = ? WHERE ID = ?")) {
				stm.setString(1, name);
				stm.setString(2, description);
				stm.setInt(3, id);
				stm.execute();
			}	
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	private void trasformResultSetToProduct(List<Product> products, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Product prod = new Product(rst.getInt(1), rst.getString(2), rst.getString(3));

				products.add(prod);
			}
		}
	}


	public List<Product> searchByCategory(Category ct) throws SQLException {
		
		List<Product> products = new ArrayList<Product>();
		
		String sql = "SELECT ID, NOME, DESCRICAO FROM PRODUTO WHERE  CATEGORIA_ID = ?";
		
		try(PreparedStatement stm = connection.prepareStatement(sql)){
			
			stm.setInt(1, ct.getId());
			stm.execute();
			
			try(ResultSet rst = stm.getResultSet()){
				
				while (rst.next()) {
					
					Product product = new Product(rst.getInt(1), rst.getString(2),rst.getString(3));
					
					products.add(product);
					
				}

			//auto close result set	
			}
			
		//auto close prepared statement	
		}
		
		return products;
	
	}
	
	
		
		
		
	
	
	
	
	
	
}
	

