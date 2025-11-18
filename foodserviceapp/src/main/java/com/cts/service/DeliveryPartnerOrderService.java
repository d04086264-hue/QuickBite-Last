package com.cts.service;

import java.util.List;

import com.cts.dto.response.OrderPlacementResponseDTO;
import com.cts.dto.response.OrderResponseDTO;
import com.cts.entity.Order;

public interface DeliveryPartnerOrderService {

	OrderPlacementResponseDTO markOrderCompleted(int orderId, String otp);
	OrderPlacementResponseDTO markOrderOutForDelivery(int orderId);
	List<OrderResponseDTO> getMyAssignedOrders();
	Order getMyAssignedOrder(int orderId);

}
