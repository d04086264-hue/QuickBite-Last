package com.cts.model;

public class DeliveryPartner extends User {
    
    public DeliveryPartner() {
        super();
    }
    
    public DeliveryPartner(Long id, String email, String name) {
        super(id, email, name, "deliveryPartner");
    }
}