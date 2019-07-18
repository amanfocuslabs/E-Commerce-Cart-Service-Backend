package com.ea.ecommercecartservicebackend.service;

import com.ea.ecommercecartservicebackend.model.Cart;
import com.ea.ecommercecartservicebackend.model.OrderLine;

import java.util.List;

public interface CartService {
    Cart getCartInformation(Long id);
    Cart createCart(Cart cart);
    Cart updateCart(Cart cart);
    Boolean checkCartExists(Long id);
    Cart addToCart(OrderLine orderLine, Long cartId);
    List<Long> getAllProductIdsInACart(Long cartId);
}
