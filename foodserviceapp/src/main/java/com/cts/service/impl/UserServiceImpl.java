package com.cts.service.impl;

import com.cts.client.AuthServiceClient;
import com.cts.dto.response.UserResponseDTO;
import com.cts.model.User;
import com.cts.service.UserService;

import lombok.AllArgsConstructor;
import com.cts.model.Customer;
import com.cts.model.DeliveryPartner;
import com.cts.model.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private AuthServiceClient authServiceClient;
    
	public User getUserById(Long id) {
            ResponseEntity<UserResponseDTO> response = authServiceClient.getUserById(id);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return convertToUserModel(response.getBody());
            }
            return null;
    }
    
  
    public User getUserByEmail(String email) {
            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            System.out.println("Encoded email: " + encodedEmail);
            ResponseEntity<UserResponseDTO> response = authServiceClient.getUserByEmail(email);
        	
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return convertToUserModel(response.getBody());
            }
        return null;
    }
    
    
    public List<UserResponseDTO> getActiveCustomers() {
            ResponseEntity<List<UserResponseDTO>> response = authServiceClient.getActiveCustomers();
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        return List.of();
    }
  
    public List<UserResponseDTO> getActiveDeliveryPartners() {
            ResponseEntity<List<UserResponseDTO>> response = authServiceClient.getActiveDeliveryPartners();
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        return List.of();
    }
    
    public List<UserResponseDTO> searchCustomersByName(String name) {
            ResponseEntity<List<UserResponseDTO>> response = authServiceClient.searchCustomersByName(name);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        return List.of();
    }
    
    public List<UserResponseDTO> searchDeliveryPartnersByName(String name) {
            ResponseEntity<List<UserResponseDTO>> response = authServiceClient.searchDeliveryPartnersByName(name);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        return List.of();
    }
 
    private User convertToUserModel(UserResponseDTO dto) {
        User user;
        
        if ("customer".equals(dto.getRole())) {
            user = new Customer();
        } else if ("deliveryPartner".equals(dto.getRole())) {
            user = new DeliveryPartner();
        } else if ("admin".equals(dto.getRole())) {
            user = new Admin();
        } else {
            user = new User();
        }
        
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPhno(dto.getPhno());
        user.setLocation(dto.getLocation());
        user.setRole(dto.getRole());
        user.setAvailabilityStatus(dto.getAvailabilityStatus());
        user.setTotalOrders(dto.getTotalOrders());
        
        return user;
    }
}