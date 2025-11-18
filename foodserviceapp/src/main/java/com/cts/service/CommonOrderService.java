package com.cts.service;

import java.util.List;

import com.cts.dto.response.OrderResponseDTO;
import com.cts.entity.Order;
import com.cts.model.User;

public interface CommonOrderService {

	List<OrderResponseDTO> getAllOrdersDtoForCurrentUser();
	User getCurrentAuthenticatedUser();
	User getUserById(Long id);
	Order getOrderById(int orderId);
	List<Order> getAllOrders();
	List<Order> getOrdersByCustomer(User customer);
	List<Order> getOrdersForCurrentUser();
	OrderResponseDTO mapToOrderResponseDto(Order order);
	List<Order> getOrdersByDeliveryPartner(com.cts.model.User partner);

}
