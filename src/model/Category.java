package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
	
	private Integer id;
	private String name;
	private List<Product> products = new ArrayList<Product>();
	
	public Category(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		
		return this.name;
	}

	public Integer getId() {
		return id;
	}
	
	public void addProduct(Product prod) {
		this.products.add(prod);
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
