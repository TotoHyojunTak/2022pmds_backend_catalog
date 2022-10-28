package com.pmds.catalog.data.dto.request;

import lombok.Data;

@Data
public class CatalogReqDTO {
    private String productId;
     private String productName;
    private Integer stock;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
