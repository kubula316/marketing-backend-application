package com.futurum.marketing.seller.service;

import com.futurum.marketing.seller.Seller;
import com.futurum.marketing.seller.dto.CreateSellerRequest;
import com.futurum.marketing.seller.dto.SellerDto;
import com.futurum.marketing.seller.dto.TopUpRequest;

import java.util.List;

public interface SellerFacade {
    SellerDto create(CreateSellerRequest request);

    SellerDto get(long sellerId);

    Seller getEntity(long sellerId);

    List<SellerDto> list();

    SellerDto topUp(long sellerId, TopUpRequest request);
}
