package com.cts.exception;

public class FoodNotInStockException extends RuntimeException {
	public FoodNotInStockException(String message){
		super(message);
	}

}
