package com.ibori.mma.order.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OrderCreateRequest(
        @NotBlank(message = "상품명은 필수입니다.") String productName,
        @Positive(message = "수량은 1 이상이어야 합니다.") int quantity
) {}