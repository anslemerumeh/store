package com.example.store.mapper;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Order;
import com.example.store.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "orderIds", source = "orders", qualifiedByName = "ordersToIds")
    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    @Named("ordersToIds")
    default List<Long> ordersToIds(List<Order> orders) {
        if (orders == null) return List.of();
        return orders.stream().map(Order::getId).toList();
    }
}
