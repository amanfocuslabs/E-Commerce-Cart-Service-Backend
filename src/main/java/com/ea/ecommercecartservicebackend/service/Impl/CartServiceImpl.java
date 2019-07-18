package com.ea.ecommercecartservicebackend.service.Impl;

import com.ea.ecommercecartservicebackend.dto.Product;
import com.ea.ecommercecartservicebackend.model.OrderLine;
import com.ea.ecommercecartservicebackend.service.CartService;
import com.ea.ecommercecartservicebackend.dao.CartRepository;
import com.ea.ecommercecartservicebackend.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart getCartInformation(Long id) {
        return cartRepository.getOne(id);
    }

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Boolean checkCartExists(Long id) {
        return cartRepository.existsById(id);
    }

    @Override
    public Cart addToCart(OrderLine orderLine, Long cartId) {
        Cart cart = cartRepository.getOne(cartId);
        cart.addOrderLine(orderLine);
        return cartRepository.save(cart);
    }

    @Override
    public List<Long> getAllProductIdsInACart(Long cartId) {

        Cart cart = cartRepository.getOne(cartId);

        List<Long> productIdList = new ArrayList<>();
        for(OrderLine orderLine: cart.getOrderLineList()){
            productIdList.add(orderLine.getProductId());
        }

        return productIdList;
    }
}
