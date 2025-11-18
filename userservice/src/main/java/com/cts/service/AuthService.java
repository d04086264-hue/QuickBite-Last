package com.cts.service;

import com.cts.dto.request.LoginRequestDTO;
import com.cts.dto.request.RegisterCustomerRequestDTO;
import com.cts.dto.request.RegisterDeliveryPartnerRequestDTO;
import com.cts.dto.response.*;

public interface AuthService {
    public RegisterCustomerResponseDTO createCustomer(RegisterCustomerRequestDTO registerRequestDTO);
    public LoginResponseDTO loginUser(LoginRequestDTO loginDTO);
    public RegisterDeliveryPartnerResponseDTO createDeliveryPartner(RegisterDeliveryPartnerRequestDTO registerDeliveryPartnerDTO);
}
