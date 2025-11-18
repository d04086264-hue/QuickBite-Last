package com.cts.service;

import java.util.List;

import com.cts.dto.response.OrderPlacementResponseDTO;
import com.cts.dto.response.OrderResponseDTO;
import com.cts.entity.Order;
import com.cts.enums.OrderStatus;

public interface AdminOrderService {

	OrderPlacementResponseDTO updateOrderStatus(int orderId, OrderStatus newStatus);
	OrderPlacementResponseDTO assignDeliveryPartner(int orderId, Long partnerId);
	List<OrderResponseDTO> getAllOrdersWithDetails();
	Order getAnyOrder(int orderId);
	
}
