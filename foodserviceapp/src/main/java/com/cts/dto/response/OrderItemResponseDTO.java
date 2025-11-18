package com.cts.dto.response;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
	
    private int id;
    private int foodId;
    private String foodName;
    private int quantity;
    private double price;
    private double subtotal;

}
