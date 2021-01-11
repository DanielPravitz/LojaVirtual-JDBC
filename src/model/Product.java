package model;

public class Product {

	private Integer id;
	private String name;
	private String description;
	private Integer categoryId;

	public Product(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
	public Product(Integer id, String name, String description) {
		
		this.id = id;
		this.name = name;
		this.description = description;
	
	}
	
	
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	
	public Integer getId() {
		return id;
	}
	
	

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {

		return this.id + ", " + this.name + ", " + this.description;
	}
	
	
	
}
