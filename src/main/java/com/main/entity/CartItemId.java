package com.main.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemId implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnoreProperties(value =  {"items"})
	private Product product;
	@ManyToOne
	@JoinColumn( name = "cart_id")
	//@JsonIgnoreProperties(value =  {"items"})
	@JsonIgnore
	private ShoppingCart cart;
	
	@Override
	public String toString() {
		return product.getId()+" "+cart.getId();
	}
}
