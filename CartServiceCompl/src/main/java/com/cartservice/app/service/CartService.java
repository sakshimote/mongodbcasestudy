package com.cartservice.app.service;

import java.util.List;

import com.cartservice.app.model.Cart;
import com.cartservice.app.model.Product;

public interface CartService {
	
	public Cart AddCart(Cart cart,String userId,String productName);
	public Cart getByCartId(String cartId);

	public String deleteCart(String cartId);
	public List<Cart> getAllCarts();
	public Cart AddQuantityOfProduct(String cartId, String productName, Integer quantity);
	public Cart RemoveQuantityOfProduct(String cartId, String productName, Integer quantity);
	
	public Cart addMoreProductsInCart(String cartId, String productName, Integer quantity);
}
