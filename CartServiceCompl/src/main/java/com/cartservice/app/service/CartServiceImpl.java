package com.cartservice.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cartservice.app.model.Cart;
import com.cartservice.app.model.ItemsAddedInCart;
import com.cartservice.app.model.Product;
import com.cartservice.app.model.UserProfile;
import com.cartservice.app.repository.CartRepository;
@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	public Cart AddCart(Cart cart, String userId, String productName) {
		
		
		// TODO Auto-generated method stub
		UserProfile profile=restTemplate.getForObject(
				"http://localhost:8095/api/user/"+userId, UserProfile.class);
		cart.setUserId(profile.getId());
		
		Product product=restTemplate.getForObject(
				"http://localhost:8096/product/allproduct/name/"+productName, Product.class);
		cart.setProductName(product.getProductName());
		cart.setProductPrice(product.getPrice());
		cart.setTotalPrice(product.getPrice()*cart.getQuantity());
		List<ItemsAddedInCart> productsAddedList=new ArrayList<>();
		ItemsAddedInCart inCart=new ItemsAddedInCart(productName,cart.getQuantity());
		productsAddedList.add(inCart);
		cart.setProductsAdded(productsAddedList);
		return cartRepository.save(cart);
		
	}

	@Override
	public Cart getByCartId(String cartId) {
		// TODO Auto-generated method stub
		return cartRepository.findById(cartId).get();
	}

	@Override
	public Cart AddQuantityOfProduct(String cartId, String productName, Integer quantity) {
		Cart cart=cartRepository.findById(cartId).get();

		Product product=restTemplate.getForObject(
				"http://localhost:8096/product/allproduct/name/"+productName, Product.class);
		cart.setCartId(cartId);
		cart.setProductName(product.getProductName());
		List<ItemsAddedInCart> list=cart.getProductsAdded();
	
		list.stream().map(i->{
			if(i.getProductName().equals(productName)) {
			i.setQuantity(i.getQuantity()+quantity);			
			}
			return i;	
			}).collect(Collectors.toList());
		
		cart.setProductsAdded(list);
		cart.setQuantity(quantity);
		cart.setProductPrice(product.getPrice()*quantity);
		cart.setTotalPrice(cart.getTotalPrice()+cart.getProductPrice());
		
		return cartRepository.save(cart);
	}
	
	
	@Override
	public Cart RemoveQuantityOfProduct(String cartId, String productName, Integer quantity) {
		Cart cart=cartRepository.findById(cartId).get();

		Product product=restTemplate.getForObject(
				"http://localhost:8096/product/allproduct/name/"+productName, Product.class);
		if(cart.getTotalPrice()==0.0) {
			throw new RuntimeException("Quantity is zero, cannot remove items from list");
		}
		
		cart.setCartId(cartId);
		cart.setProductName(product.getProductName());
		List<ItemsAddedInCart> list=cart.getProductsAdded();

		list.stream().map(i->{
			if(i.getProductName().equals(productName)) {
			i.setQuantity(i.getQuantity()-quantity);			
			}
			return i;	
			}).collect(Collectors.toList());
		
		cart.setProductsAdded(list);
		cart.setQuantity(quantity);
		cart.setProductPrice(product.getPrice()*quantity);
		cart.setTotalPrice(cart.getTotalPrice()-cart.getProductPrice());
		
		return cartRepository.save(cart);
	}
	



	@Override
	public String deleteCart(String cartId) {
		Cart cart=cartRepository.findById(cartId).get();
		// TODO Auto-generated method stub
		 cartRepository.delete(cart);
		 return "Cart Deleted Successfully";
	}
	@Override
	public List<Cart> getAllCarts(){
		return cartRepository.findAll();
	}

	@Override
	public Cart addMoreProductsInCart(String cartId, String productName, Integer quantity) {
		// TODO Auto-generated method stub
		Cart cart=cartRepository.findById(cartId).get();

		Product product=restTemplate.getForObject(
				"http://localhost:8096/product/allproduct/name/"+productName, Product.class);
		cart.setCartId(cartId);
		cart.setProductName(product.getProductName());
				
		List<ItemsAddedInCart> list=cart.getProductsAdded();
		
	

			ItemsAddedInCart newItems=new ItemsAddedInCart();
			newItems.setProductName(productName);
			newItems.setQuantity(quantity);
			list.add(newItems);
			cart.setProductsAdded(list);
			cart.setProductPrice(product.getPrice());
			cart.setQuantity(quantity);
			cart.setTotalPrice(cart.getTotalPrice()+ product.getPrice()*quantity);
		
		
		
		return cartRepository.save(cart);
	}
	
}
