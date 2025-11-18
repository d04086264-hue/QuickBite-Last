package com.cts.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column( nullable = false)
	private String firstName;
    @Column( nullable = false)
	private String lastName;
	 @Column( nullable = false)
	private String street;
	 @Column( nullable = false)
	private String city;
	 @Column( nullable = false)
	private String state;
	 @Column( nullable = false)
	private String pin;
	 @Column( nullable = false)
	private String phoneNo;
	@OneToOne
	@JoinColumn(name="order_id")
	private Order order;
	
}
