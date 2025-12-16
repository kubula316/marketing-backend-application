package com.futurum.marketing.product.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateProductRequest(@NotBlank String name) {
}
