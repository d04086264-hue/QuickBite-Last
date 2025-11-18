package com.cts.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CartResponseDTO {
    
    private Long id;
    private int foodId;
    private String foodName;
    private String foodImage;
    private String categoryName;
    private double price;
    private int quantity;
    private double totalPrice;
    private LocalDateTime createdAt;

}
