package com.cts.service;

import java.util.List;

import com.cts.dto.response.UserResponseDTO;
import com.cts.model.User;

public interface UserService {

	User getUserByEmail(String email);
	User getUserById(Long id);
	List<UserResponseDTO> getActiveCustomers();
	List<UserResponseDTO> getActiveDeliveryPartners();
	List<UserResponseDTO> searchCustomersByName(String name);
	List<UserResponseDTO> searchDeliveryPartnersByName(String name);

}
