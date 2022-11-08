package com.main.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class CartItem {

	
	@EmbeddedId
	private CartItemId cartItemId;
	private int itemCount;
	@JsonIgnoreProperties(value =  {"items"})
	public Product getProduct() {
		return cartItemId.getProduct();
	}
	public void setProduct(Product pro) {
		 cartItemId.setProduct(pro);
	}
//	@JsonIgnoreProperties(value =  {"items"})
	@JsonIgnore
	public ShoppingCart getCart() {
		return cartItemId.getCart();
	}
	public void setCart(ShoppingCart cart) {
		 cartItemId.setCart(cart);
	}
	
	@Override
	public String toString() {
		return "CartItem [ product=" + getProduct() + ", itemCount=" + itemCount + "]";
	}
	
	
}
