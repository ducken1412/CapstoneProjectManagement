package com.fa.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.dto.CartItem;
import com.fa.dto.ProductDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class CartServiceImpl implements CartService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductService productService;

	/**
	 * Return a list<CartItem> from a json.
	 * 
	 * @param cart
	 * @return
	 */
	@Override
	public List<CartItem> getCartItems(String cart) {
		if (!"null".equals(cart)) {
			List<CartItem> cartItems = null;
			Gson gson = new Gson();
			Type cartListType = new TypeToken<ArrayList<CartItem>>() {
			}.getType();

			try {
				cartItems = gson.fromJson(cart, cartListType);
				return cartItems;
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				return new ArrayList<>();
			}
		}
		return new ArrayList<CartItem>();

	}

	/**
	 * Return list<ProductDTO> in datadase.
	 * 
	 * @param cartItems
	 * @return
	 */
	@Override
	public List<ProductDTO> getProductDTOs(List<CartItem> cartItems) {
		List<ProductDTO> products = new ArrayList<>();
		ProductDTO productDTO = null;
		Iterator<CartItem> cartIterator = cartItems.iterator();
		CartItem item = null;
		while (cartIterator.hasNext()) {
			item = cartIterator.next();
			productDTO = productService.getProductAvailable(item.getId());
			if (productDTO != null && item.getQuantity() > 0) {
				productDTO.setDescription(productDTO.getQuantity().toString());
				productDTO.setQuantity(item.getQuantity());
				products.add(productDTO);
			} else {
				cartIterator.remove();
			}
		}
		return products;
	}

	/**
	 * check product change in database and change list<Product> param.
	 * 
	 * @param cartItems
	 * @param products
	 * @return
	 */
	@Override
	public boolean checkCart(List<CartItem> cartItems, List<ProductDTO> products) {
		boolean check = true;
		ProductDTO productDTO = null;
		Iterator<CartItem> cartIterator = cartItems.iterator();
		CartItem item = null;
		while (cartIterator.hasNext()) {
			item = cartIterator.next();
			productDTO = productService.getProductAvailable(item.getId());
			if (productDTO != null && item.getQuantity() > 0) {
				if (productDTO.getQuantity() < item.getQuantity()) {
					check = false;
				}
				productDTO.setDescription(productDTO.getQuantity().toString());
				productDTO.setQuantity(item.getQuantity());
				products.add(productDTO);
			} else {
				cartIterator.remove();
				check = false;
			}
		}
		return check && !products.isEmpty();
	}
}
