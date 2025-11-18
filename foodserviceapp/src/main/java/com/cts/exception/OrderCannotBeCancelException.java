package com.cts.exception;

public class OrderCannotBeCancelException extends RuntimeException {
	public OrderCannotBeCancelException(String message){
		super(message);
	}
}
