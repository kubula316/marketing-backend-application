package com.futurum.marketing.seller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateSellerRequest(
        @NotBlank String name,
        @NotNull @DecimalMin(value = "0.00") BigDecimal initialEmeraldBalance
) {
}
