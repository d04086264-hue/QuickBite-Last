package com.cts.dto.response;

import com.cts.entity.Category;

import lombok.Data;

@Data
public class FoodResponseDTO {
	
    private int id;
    private String name;
    private String img;
    private double price;
    private String description;
    private boolean status;
    private int avgRating;
    private Category category;

}
