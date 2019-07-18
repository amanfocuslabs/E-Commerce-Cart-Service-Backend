package com.ea.ecommercecartservicebackend.dto;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String productName;
    private String shortProductDescription;
    private Long price;
    private String imageURL;
    private ProductInformation productInformation;
    private ProductCategory productCategory;
}
