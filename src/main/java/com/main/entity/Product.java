package com.main.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Min(value = 1,message = "{price.violation}")
	private double price;
	
	@NotEmpty(message = "{name.violation}")
	private String name;
	private String imgPath;
	private Boolean isActive;
	private long unitNumbers;
	private String description;
	private String category;
	@OneToMany(mappedBy = "cartItemId.product")
	//@JsonIgnoreProperties(value ={"cartItemId.product"})
	@JsonIgnore
	private List<CartItem> items = new ArrayList<>();

	@Override
	public String toString() {
		return "Product [id=" + id + ", price=" + price + ", name=" + name + ", imgPath=" + imgPath + ", isActive="
				+ isActive + ", unitNumbers=" + unitNumbers + ", description=" + description + ", category=" + category
				+ "]";
	}
	
	
	
	
}
