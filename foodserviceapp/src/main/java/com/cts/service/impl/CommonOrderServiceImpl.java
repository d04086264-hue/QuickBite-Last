package com.cts.service.impl;

import com.cts.dto.request.OrderAddressDTO;
import com.cts.dto.response.OrderItemResponseDTO;
import com.cts.dto.response.OrderResponseDTO;
import com.cts.entity.Order;
import com.cts.entity.OrderAddress;
import com.cts.entity.OrderItem;
import com.cts.repository.OrderAddressRepository;
import com.cts.repository.OrderItemRepository;
import com.cts.repository.OrdersRepository;
import com.cts.service.CommonOrderService;
import com.cts.service.UserService;
import lombok.AllArgsConstructor;
import com.cts.exception.OrderNotFoundException;
import com.cts.exception.UserNotFoundException;
import com.cts.exception.UserRoleIsNotValidException;
import com.cts.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CommonOrderServiceImpl implements CommonOrderService{
    
    private final OrdersRepository orderRepository;
    private final UserService userService;
    private final OrderAddressRepository orderAddressRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper mapper;
    
   
    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return user;
    }
    

    public User getUserById(Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        return user;
    }
    

    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }
    

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByIdDesc();
    }
    

    public List<Order> getOrdersByCustomer(User customer) {
        return orderRepository.findByCustomerOrderByIdDesc(customer.getId());
    }
    

    public List<Order> getOrdersByDeliveryPartner(User partner) {
        return orderRepository.findByDeliveryPartnerOrderByIdDesc(partner.getId());
    }
    
   
    public List<Order> getOrdersForCurrentUser() {
        User user = getCurrentAuthenticatedUser();
        
        if (user instanceof com.cts.model.Admin) {
            return getAllOrders();
        } else if (user instanceof com.cts.model.Customer) {
            return getOrdersByCustomer(user);
        } else if (user instanceof com.cts.model.DeliveryPartner) {
            return getOrdersByDeliveryPartner(user);
        } else {
            throw new UserRoleIsNotValidException("Invalid user role");
        }
    }
    
   
    public List<OrderResponseDTO> getAllOrdersDtoForCurrentUser() {
        List<Order> orders = getOrdersForCurrentUser();
        
        return orders.stream()
            .map(this::mapToOrderResponseDto)
            .collect(Collectors.toList());
    }
    
    public OrderResponseDTO mapToOrderResponseDto(Order order) {
        OrderResponseDTO dto = mapper.map(order, OrderResponseDTO.class);
        
        
        if (order.getCustomer() != 0) {
            User customer = userService.getUserById(order.getCustomer());
            if (customer != null) {
                dto.setCustomerId(Math.toIntExact(customer.getId()));
                dto.setCustomerName(customer.getName());
            }
        }
        
       
        if (order.getDeliveryPartner() != 0) {
            User deliveryPartner = userService.getUserById(order.getDeliveryPartner());
            if (deliveryPartner != null) {
                dto.setDeliveryPartnerId(Math.toIntExact(deliveryPartner.getId()));
                dto.setAssignDeliveryPerson(deliveryPartner.getName());
            }
        }
       
        OrderAddress orderAddress = orderAddressRepository.findByOrder(order);
        if (orderAddress != null) {
            dto.setOrderAddress(mapper.map(orderAddress, OrderAddressDTO.class));
        }
        
       
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        if (!orderItems.isEmpty()) {
            List<OrderItemResponseDTO> itemDTOs = orderItems.stream()
                .map(item -> {
                    OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                    itemDTO.setId(item.getId());
                    if (item.getFood() != null) {
                        itemDTO.setFoodId(item.getFood().getId());
                        itemDTO.setFoodName(item.getFood().getName());
                    }
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setPrice(item.getPrice());
                    itemDTO.setSubtotal(item.getQuantity() * item.getPrice());
                    return itemDTO;
                })
                .collect(Collectors.toList());
            dto.setOrderItems(itemDTOs);
        }
        
        return dto;
    }
}
