package com.main.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.entity.AppUser;
import com.main.entity.CartItem;
import com.main.entity.CartItemId;
import com.main.entity.Product;
import com.main.entity.ShoppingCart;
import com.main.service.ProductService;
import com.main.service.ShoppingCartService;
import com.main.service.UserService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;
	@Autowired
	private UserService userService;
	@Autowired
	private ShoppingCartService cartService;
	private static Map<String, String> categories = new HashMap<>();
	static {
		
		categories.put("book", "Books");
		categories.put("mobile", "Mobiles");
		categories.put("laptop", "Laptops");
		categories.put("other", "Others");
	}
	
	@GetMapping
	public String welcome() {
		return "welcome";
	}
	
	@GetMapping("/all")
	public String allProducts(Model model,Principal principal) {
		List<Product>products = service.allProducts();
		model.addAttribute("categories", categories.entrySet());
		model.addAttribute("products", products);
		model.addAttribute("user", getUserName(principal));
		model.addAttribute("cart", getCartInfo(principal));
		return "home";
	}
	
	
	@GetMapping("/id/{id}")
	public String findById(@PathVariable("id") long id,Model model,Principal principal) {
		model.addAttribute("user", getUserName(principal));
		Product pro = service.findById(id);
		model.addAttribute("product", pro);
		ShoppingCart cart = getCartInfo(principal);
		if(principal != null) {
			model.addAttribute("cart", cart);
		}
		
		CartItemId cartItemId = new CartItemId(pro,cart);
		CartItem item = new CartItem();
		item.setCartItemId(cartItemId);
		item.setProduct(pro);
		model.addAttribute("item",item);
		return "product-spec";
	}
	
	@GetMapping("/category/{categ}")
	public String productsByCategory(@PathVariable("categ")String categ,Model model,Principal principal) {
		model.addAttribute("user", getUserName(principal));
		List<Product>products = service.findByCategory(categ);
		model.addAttribute("categories", categories.entrySet());
		model.addAttribute("products", products);
		model.addAttribute("cart", getCartInfo(principal));
		return "home";
	}
	
	@GetMapping("/name/{name}")
	public String productsByName(@PathVariable("name")String name,Model model,Principal principal) {
		
		model.addAttribute("user", getUserName(principal));
		List<Product>products = service.findByName(name);
		model.addAttribute("categories", categories.entrySet());
		model.addAttribute("products", products);
		model.addAttribute("cart", getCartInfo(principal));
		return "home";
	}
	
	@GetMapping("/add")
	public String addProduct(Model model) {
		Product product = new Product();
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);
		return "add-product";
	}
	@GetMapping("/update/{id}")
	public String updateProduct(@PathVariable("id")long id, Model model) {
		Product product = service.findById(id);
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);
		return "update-product";
	}
	@PostMapping("/update/validate")
	public String updateProductValidation(@Valid @ModelAttribute("product") Product product,BindingResult rs
			,HttpServletRequest r,Model model,Principal pricipal) {
		if(rs.hasErrors())return "update-product";
		Part file=null;
		try {
			file = r.getPart("file");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		service.update(product,file);
		return allProducts(model,pricipal);
	}
	@PostMapping("/validate")
	public String validateProduct(@Valid @ModelAttribute("product") Product product,BindingResult rs,HttpServletRequest r) {
		if(rs.hasErrors())return "add-product";
		Part file=null;
		try {
			file = r.getPart("file");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		service.insert(product,file);
		return "add-product";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id")long id) {
		service.deleteById(id);
		return "redirect:/products/all";
	}
	private String getUserName(Principal principal) {
		String user = "UN";
		System.out.println(principal);
		if (principal != null) {
			user=principal.getName().substring(0,2);
		}
		return user;
	}
	private ShoppingCart getCartInfo(Principal principal) {
		ShoppingCart cart = null;
		if (principal != null) {
			AppUser user = userService.findByEmail(principal.getName());
			 cart = user.getCart();
			if(cart == null) {
				cart = cartService.addCartToUser(user);
			}
		}
		return cart;
		
	}
}
