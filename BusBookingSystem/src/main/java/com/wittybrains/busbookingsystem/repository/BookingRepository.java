package com.wittybrains.busbookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wittybrains.busbookingsystem.model.Booking;
import com.wittybrains.busbookingsystem.model.MyUser;


public interface BookingRepository extends JpaRepository<Booking, Long> {
	   @SuppressWarnings("unchecked")
	Booking save(Booking booking);
    List<Booking> findByUser(MyUser user);
}
