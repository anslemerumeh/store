package com.example.store.controller;

import com.example.store.entity.Product;
import com.example.store.mapper.CustomerMapper;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ComponentScan(basePackageClasses = {CustomerMapper.class, ProductMapper.class})
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setDescription("Test Product");
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productRepository.save(product)).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test Product"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(productRepository.findAllWithOrders()).thenReturn(List.of(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Test Product"))
                .andExpect(jsonPath("$[0].orderIds").isArray());
    }

    @Test
    void testGetProductById() throws Exception {
        when(productRepository.findByIdWithOrders(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Product"))
                .andExpect(jsonPath("$.orderIds").isArray());
    }

    @Test
    void testGetProductByIdNotFound() throws Exception {
        when(productRepository.findByIdWithOrders(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound());
    }
}
