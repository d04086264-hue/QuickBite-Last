package com.cts.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FoodRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Image URL is required")
    private String img;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private double price;
    
    private String description;
    private boolean status;

    @NotBlank(message = "CategoryName is required")
    private String categoryName;

}
