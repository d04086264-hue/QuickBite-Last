package com.cts.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderAddressDTO {

	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must contain only alphabets")
    private String firstName;
	
    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last Name must contain only alphabets")
    private String lastName;
    
    @NotBlank(message = "Street is required")
    private String street;
    
	@NotBlank(message = "City is required")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "City must contain only alphabets")
    private String city;
	
	@NotBlank(message = "State is required")
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "State must contain only alphabets")
    private String state;
	
	@NotBlank(message = "Pin is required")
	@Pattern(regexp = "^[0-9]{6}$", message = "PIN code must be exactly 6 digits")
    private String pin;
	
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNo;
	
}