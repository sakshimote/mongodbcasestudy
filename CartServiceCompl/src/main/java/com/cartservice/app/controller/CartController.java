package com.cartservice.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartservice.app.model.Cart;
import com.cartservice.app.service.CartServiceImpl;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartServiceImpl cartServiceImpl;
	
	@PostMapping("/addcart/{userId}/{productName}")
	public Cart AddCart(@RequestBody Cart cart, @PathVariable("userId") String userId,
			@PathVariable("productName") String productName) {
		return cartServiceImpl.AddCart(cart, userId, productName);
	}
	
	@GetMapping("/getCart/{cartId}")
	public Cart getByCartId(@PathVariable("cartId") String cartId) {
		return cartServiceImpl.getByCartId(cartId);
	}
	
	@PostMapping("/add/quantity/{cartId}/{productName}/{quantity}")
	public Cart AddQuantityOfProduct(@PathVariable("cartId")String cartId,
		@PathVariable("productName")	String productName,@PathVariable("quantity") Integer quantity) {
		return cartServiceImpl.AddQuantityOfProduct(cartId, productName, quantity);
	}
	
	
	
	@DeleteMapping("/delete/{cartId}")
	public String deleteCart(@PathVariable("cartId") String cartId) {
		return cartServiceImpl.deleteCart(cartId);
	}
	
	@GetMapping("/carts")
	public List<Cart> getAllCarts(){
		return cartServiceImpl.getAllCarts();
	}
	
	@PostMapping("/remove/quantity/{cartId}/{productName}/{quantity}")
	public Cart RemoveQuantityOfProduct(@PathVariable("cartId") String cartId, 
			@PathVariable("productName")	String productName, 
			@PathVariable("quantity")Integer quantity) {
		return cartServiceImpl.RemoveQuantityOfProduct(cartId, productName, quantity);
	}
	@PostMapping("/add/items/{cartId}/{productName}/{quantity}")
	public Cart addMoreProductsInCart(@PathVariable("cartId") String cartId,
			@PathVariable("productName")	String productName,
			@PathVariable("quantity")Integer quantity) {
		return cartServiceImpl.addMoreProductsInCart(cartId, productName, quantity);
	}

}
