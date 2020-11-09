package com.fa.service;

import java.util.List;

import com.fa.dto.CartItem;
import com.fa.dto.ProductDTO;

public interface CartService {
	/**
	 * Return a list<CartItem> from a json.
	 * 
	 * @param cart
	 * @return
	 */
	List<CartItem> getCartItems(String cart);

	/**
	 * Return list<ProductDTO> in datadase.
	 * 
	 * @param cartItems
	 * @return
	 */
	List<ProductDTO> getProductDTOs(List<CartItem> cartItems);

	/**
	 * check product change in database and change list<Product> param.
	 * 
	 * @param cartItems
	 * @param products
	 * @return
	 */
	boolean checkCart(List<CartItem> cartItems, List<ProductDTO> products);

}