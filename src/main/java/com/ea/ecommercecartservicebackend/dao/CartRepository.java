package com.ea.ecommercecartservicebackend.dao;

import com.ea.ecommercecartservicebackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
