package com.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.AppUser;
import com.main.entity.CartItem;
import com.main.entity.Product;
import com.main.entity.ShoppingCart;
import com.main.repo.ShoppingCartRepo;

@Service
public class ShoppingCartService{

	@Autowired
	private ShoppingCartRepo repo;
	@Autowired
	private UserService userService;
	@Autowired
	private CartItemService itemService;
	
	
	public ShoppingCart findById(long id) {
		return repo.findById(id).get();
	}

	public List<ShoppingCart> findAll() {
		return repo.findAll();
	}
	@Transactional
	public ShoppingCart updateCartItem(AppUser user,CartItem item) {
		ShoppingCart cart = user.getCart();
		item.setCart(cart);
		int count = item.getItemCount();
		item = itemService.findById(item.getCartItemId());
		item.setItemCount(count);	
		return cart;
	}
	
	@Transactional
	public ShoppingCart removeCartItem(AppUser user,CartItem item) {
		ShoppingCart cart = user.getCart();
		item.setCart(cart);
		item = itemService.findById(item.getCartItemId());
		itemService.deleteById(item.getCartItemId());
		cart.removeItem(item);
		return cart;
	}
	@Transactional
	public ShoppingCart addCartItemToCart(AppUser user,CartItem item) {
		ShoppingCart cart = user.getCart();
		if(cart == null) {
			cart = new ShoppingCart();
			user.setCart(cart);
		}
		item.setCart(cart);
		if(itemService.findById(item.getCartItemId()) != null) {
			return updateCartItem(user, item);
		}
		item = itemService.insert(item);
		cart.addItem(item);
		cart = insert(cart);
		userService.update(user);	
		return cart;
	}
	@Transactional
	public ShoppingCart addCartToUser(AppUser user) {
		ShoppingCart cart = new ShoppingCart();
		cart =insert(cart);
		user.setCart(cart);
		userService.update(user);
		return cart;
	}
	public void deleteById(long id) {
		repo.deleteById(id);
	}

	public Map<Product,Integer> getCartProducts(ShoppingCart cart) {
		Map<Product,Integer> prods = new HashMap<>();
		Set<CartItem> items = cart.getItems();
		items.forEach((pro)->{
			prods.put(pro.getProduct(),pro.getItemCount());
		});
		
		return prods;
		
	}
	public ShoppingCart insert(ShoppingCart p) {
		return repo.save(p);
	}

	public ShoppingCart update(ShoppingCart p) {
		return repo.save(p);
	}

	
	

}
