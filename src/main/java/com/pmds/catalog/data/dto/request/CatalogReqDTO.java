package com.pmds.catalog.data.dto.request;

import lombok.Data;

@Data
public class CatalogReqDTO {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
