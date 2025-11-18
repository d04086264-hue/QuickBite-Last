package com.cts.exception;

public class OrderCannotBeMarkException extends RuntimeException {
	public OrderCannotBeMarkException(String message){
		super(message);
	}
}
