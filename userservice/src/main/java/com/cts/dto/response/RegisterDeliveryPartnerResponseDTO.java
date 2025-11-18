package com.cts.dto.response;

import lombok.Data;

@Data
public class RegisterDeliveryPartnerResponseDTO {
	
    private String email;
    private String name;
    private String phno;
    private String location;
    private int totalOrders;
    
}
