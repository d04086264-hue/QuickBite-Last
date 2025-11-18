package com.cts.service.impl;

import com.cts.config.JWTUtil;
import com.cts.dto.request.LoginRequestDTO;
import com.cts.dto.request.RegisterCustomerRequestDTO;
import com.cts.dto.request.RegisterDeliveryPartnerRequestDTO;
import com.cts.dto.response.*;
import com.cts.entity.Customer;
import com.cts.entity.DeliveryPartner;
import com.cts.exception.InvalidCredentialsException;
import com.cts.exception.UserAlreadyExistsException;
import com.cts.repository.CustomerRepository;
import com.cts.repository.DeliveryPartnerRepository;
import com.cts.repository.UserRepository;
import com.cts.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cts.entity.User;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	   
    private UserRepository userRepo;
    private CustomerRepository cusRepo;
    private DeliveryPartnerRepository dpRepo;
    private JWTUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  

	public RegisterCustomerResponseDTO createCustomer(RegisterCustomerRequestDTO registerRequestDTO) {
        if (userRepo.findByEmail(registerRequestDTO.getEmail()) != null) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        if(userRepo.findByPhno(registerRequestDTO.getPhno())!=null) {
        		throw new UserAlreadyExistsException("Phno already exists");
        }

        Customer newCustomer = new Customer();
        newCustomer.setEmail(registerRequestDTO.getEmail());
        newCustomer.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword())); 
        newCustomer.setName(registerRequestDTO.getName());
        newCustomer.setPhno(registerRequestDTO.getPhno());
        newCustomer.setLocation(registerRequestDTO.getLocation());
        newCustomer.setStatus(true);

        Customer savedCustomer = cusRepo.save(newCustomer);

        RegisterCustomerResponseDTO responseDTO = new RegisterCustomerResponseDTO();
        responseDTO.setEmail(savedCustomer.getEmail());
        responseDTO.setName(savedCustomer.getName());
        responseDTO.setPhno(savedCustomer.getPhno());
        responseDTO.setLocation(savedCustomer.getLocation());
        return responseDTO;
    }

    public RegisterDeliveryPartnerResponseDTO createDeliveryPartner(RegisterDeliveryPartnerRequestDTO registerDeliveryPartnerDTO) {
        if (userRepo.findByEmail(registerDeliveryPartnerDTO.getEmail()) != null) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        DeliveryPartner newDeliveryPartner = new DeliveryPartner();
        newDeliveryPartner.setEmail(registerDeliveryPartnerDTO.getEmail());
        newDeliveryPartner.setPassword(passwordEncoder.encode(registerDeliveryPartnerDTO.getPassword())); // hash password
        newDeliveryPartner.setName(registerDeliveryPartnerDTO.getName());
        newDeliveryPartner.setPhno(registerDeliveryPartnerDTO.getPhno());
        newDeliveryPartner.setLocation(registerDeliveryPartnerDTO.getLocation());
        newDeliveryPartner.setStatus(true);

        DeliveryPartner savedDeliveryPartner = dpRepo.save(newDeliveryPartner);

        RegisterDeliveryPartnerResponseDTO responseDTO = new RegisterDeliveryPartnerResponseDTO();
        responseDTO.setEmail(savedDeliveryPartner.getEmail());
        responseDTO.setName(savedDeliveryPartner.getName());
        responseDTO.setPhno(savedDeliveryPartner.getPhno());
        responseDTO.setLocation(savedDeliveryPartner.getLocation());
        return responseDTO;
    }


    public LoginResponseDTO loginUser(LoginRequestDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (Exception ex) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User existingUser = userRepo.findByEmail(loginDTO.getEmail());
        if (existingUser == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String role = existingUser.getRole();
        String normalizedRole = role
            .replaceAll("([a-z])([A-Z])", "$1_$2")  
            .toUpperCase();  

        String token = jwtUtil.generateToken(
            existingUser.getEmail(), 
            existingUser.getId(), 
            normalizedRole
        );

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setId(existingUser.getId());
        responseDTO.setEmail(existingUser.getEmail());
        responseDTO.setName(existingUser.getName());
        responseDTO.setRole(existingUser.getRole());
        responseDTO.setAccessToken(token);
        return responseDTO;
    }

}