package com.ea.ecommercecartservicebackend.controller;

import com.ea.ecommercecartservicebackend.dto.OrderLine;
import com.ea.ecommercecartservicebackend.dto.Product;
import com.ea.ecommercecartservicebackend.model.Cart;
import com.ea.ecommercecartservicebackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private RestTemplate restTemplate;

    private String order_service_url = "http://order-service:8081/rest/order/";
    private String product_service_url = "http://product-service:8084/rest/product/";

    @GetMapping("/{id}")
    public Cart getCartInformation(@PathVariable Long id){
        return cartService.getCartInformation(id);
    }

    @PostMapping("/create")
    public Cart createNewCart(Cart cart){
        return cartService.createCart(cart);
    }

    @PostMapping("/addToCart/{productId}/{quantity}/{cartId}")
    public Cart addToCart(@PathVariable Long cartId, @PathVariable Long productId,@PathVariable int quantity, Model model){
        OrderLine orderLine = restTemplate.postForObject(order_service_url + "/orderLine/create/" + productId + "/" + quantity, null, OrderLine.class);

        com.ea.ecommercecartservicebackend.model.OrderLine orderLinePersist = new com.ea.ecommercecartservicebackend.model.OrderLine();
        orderLinePersist.setProductId(orderLine.getProductId());
        orderLinePersist.setQuantity(orderLine.getQuantity());


        if(cartService.checkCartExists(cartId)){
            Cart cart = cartService.getCartInformation(cartId);
            cart.addOrderLine(orderLinePersist);
            System.out.println(cart.getOrderLineList().get(1).getProductId());
            return cartService.updateCart(cart);
        }

        Cart cart = new Cart();
        cart.addOrderLine(orderLinePersist);
        return cartService.createCart(cart);
    }

    @GetMapping("/getAllProducts/{id}")
    public List<Product> getAllProductsInACart(@PathVariable Long id){
        // Todo get a list of productId then call the product service and get a List of Products
        List<Long> productIds = cartService.getAllProductIdsInACart(id);

        return productIds.stream()
                .map(productId ->
                        restTemplate.getForObject(product_service_url + "/" + productId, Product.class)
                ).collect(Collectors.toList());
    }
}
