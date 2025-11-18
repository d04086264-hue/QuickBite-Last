package com.cts.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDeliveryPartnerRequestDTO {
	
	@NotBlank(message = "Email is required")
	@Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$",message="Email id is invalid format")
    private String email;
	
	@NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
	
	@NotBlank(message = "Name is required")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only alphabets")
    private String name;
	
	@NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phno;
	
	@NotBlank(message = "Location is required")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "location must contain only alphabets and No Space")
    private String location;

}
