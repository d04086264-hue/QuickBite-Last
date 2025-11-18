package com.cts.service;

import java.util.List;

import com.cts.dto.request.OrderPlacementRequestDTO;
import com.cts.dto.response.OrderPlacementResponseDTO;
import com.cts.dto.response.OrderResponseDTO;
import com.cts.entity.Order;

import jakarta.validation.Valid;

public interface CustomerOrderService {

	OrderPlacementResponseDTO placeOrder(@Valid OrderPlacementRequestDTO request);
	Order getMyOrder(int orderId);
	OrderPlacementResponseDTO cancelOrder(int orderId);
	List<OrderResponseDTO> getMyOrders();
	
}
