package com.example.productsservice.service;

import com.example.productsservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("1", "Produit A"));
        products.add(new Product("2", "Produit B"));
        return products;
    }
} 