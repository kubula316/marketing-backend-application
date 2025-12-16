package com.futurum.marketing.product.service;

import com.futurum.marketing.product.Product;
import com.futurum.marketing.product.dto.CreateProductRequest;
import com.futurum.marketing.product.dto.ProductDto;

import java.util.List;

public interface ProductFacade {
    ProductDto create(long sellerId, CreateProductRequest request);

    ProductDto get(long productId);

    Product getEntity(long productId);

    List<ProductDto> listBySeller(long sellerId);

}
