package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.entity.DeliveryPartner;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Long> {

	DeliveryPartner findByEmail(String email);

}
