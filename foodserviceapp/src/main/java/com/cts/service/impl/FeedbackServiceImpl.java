package com.cts.service.impl;

import java.time.LocalDate;
import java.util.List;

import com.cts.dto.request.FoodFeedbackRequestDTO;
import com.cts.dto.response.FoodFeedbackResponseDTO;
import com.cts.entity.Order;
import com.cts.enums.OrderStatus;
import com.cts.exception.UnauthorizedActionException;
import com.cts.exception.UserNotFoundException;
import com.cts.repository.*;
import com.cts.service.CommonOrderService;
import com.cts.service.FeedbackService;
import com.cts.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.cts.entity.Feedback;
import com.cts.entity.Food;
import com.cts.exception.FeedbackAlreadyProvidedException;
import com.cts.exception.FoodNotFoundException;
import com.cts.exception.OrderNotFoundException;
import com.cts.model.User;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    
    private ModelMapper modelMapper;
    private FeedbackRepository feedbackRepo;
    private UserService userService;
    private FoodRepository foodRepo;
    private CommonOrderService commonOrderService;
    

	public FoodFeedbackResponseDTO submitFoodFeedback(
            FoodFeedbackRequestDTO foodFeedbackRequestDTO,
            Integer foodId) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        long customerId = user.getId();
        int orderId = foodFeedbackRequestDTO.getOrderId();
        
        Order order = commonOrderService.getOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
        
        if (order.getCustomer() != customerId) {
            throw new UnauthorizedActionException("You can only provide feedback for your own orders");
        }
        
        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new UnauthorizedActionException("You can only provide feedback for delivered orders");
        }
        
        boolean foodExistsInOrder = order.getOrderItems().stream()
                .anyMatch(orderItem -> orderItem.getFood().getId() == foodId);
        
        if (!foodExistsInOrder) {
            throw new FoodNotFoundException("Food item not found in this order");
        }
        
        if (feedbackRepo.existsByOrderIdAndCustomerAndFoodId(orderId, customerId, foodId)) {
            throw new FeedbackAlreadyProvidedException("Feedback already provided for this food in this order");
        }

        Feedback feedback = new Feedback();
        feedback.setDateOfFeedback(LocalDate.now());
        feedback.setCustomer(customerId);
        feedback.setOrderId(orderId);
        
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new FoodNotFoundException("Food not found"));
        feedback.setFood(food);
       
       
        feedback.setFoodRating(foodFeedbackRequestDTO.getFoodRating());
       
        Feedback savedFeedback = feedbackRepo.save(feedback);
        
        Float avgRatingDouble = feedbackRepo.findAverageRatingByFoodId(foodId);
        if (avgRatingDouble != null) {
            float avgRating = avgRatingDouble.floatValue();
            food.setAvgRating(avgRating);
            foodRepo.save(food);
        }

        return convertToFoodFeedbackDTO(savedFeedback);
    }


    public FoodFeedbackResponseDTO convertToFoodFeedbackDTO(Feedback feedback) {
        FoodFeedbackResponseDTO dto = modelMapper.map(feedback, FoodFeedbackResponseDTO.class);
        if (feedback.getFood() != null) {
            dto.setFoodId(feedback.getFood().getId());
        } else {
            dto.setFoodId(null);
        }
        if (feedback.getOrderId() != null) {
            dto.setOrderId(feedback.getOrderId());
        } else {
            dto.setOrderId(null);
        }
        return dto;
    }

   public List<FoodFeedbackResponseDTO> getFeedbackByFoodId(Integer foodId) {
        List<Feedback> feedbacks = feedbackRepo.findByFoodId(foodId);
        if (feedbacks.isEmpty()) {
           throw new FoodNotFoundException("No feedback found for Food ID: " + foodId);
       }
        return feedbacks.stream()
                .map(this::convertToFoodFeedbackDTO)
                .collect(java.util.stream.Collectors.toList());
   }

 public List<Order> getCompletedOrdersForFeedback() {
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String email = authentication.getName();
     User user = userService.getUserByEmail(email);
     if (user == null) {
         throw new UserNotFoundException("User not found with email: " + email);
     }
     
     return commonOrderService.getOrdersByCustomer(user).stream()
             .filter(order -> order.getOrderStatus() == OrderStatus.DELIVERED)
             .collect(java.util.stream.Collectors.toList());
 }
 
 public boolean isFoodRatingGiven(int orderId, long customerId, int foodId) {
     return feedbackRepo.existsByOrderIdAndCustomerAndFoodId(orderId, customerId, foodId);
 }

  }


