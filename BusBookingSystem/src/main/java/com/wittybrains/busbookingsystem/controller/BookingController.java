package com.wittybrains.busbookingsystem.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wittybrains.busbookingsystem.dto.BookingDTO;
import com.wittybrains.busbookingsystem.dto.BusDTO;
import com.wittybrains.busbookingsystem.dto.MyUserDTO;
import com.wittybrains.busbookingsystem.model.Booking;
import com.wittybrains.busbookingsystem.model.Bus;
import com.wittybrains.busbookingsystem.model.MyUser;
import com.wittybrains.busbookingsystem.model.TravelSchedule;
import com.wittybrains.busbookingsystem.repository.BookingRepository;
import com.wittybrains.busbookingsystem.repository.BusRepository;
import com.wittybrains.busbookingsystem.repository.TravelScheduleRepository;
import com.wittybrains.busbookingsystem.repository.UserRepository;
@JsonInclude(value = Include.NON_NULL)
@RestController
@RequestMapping(value="/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    
  @Autowired
  private BusRepository busRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TravelScheduleRepository travelScheduleRepository;
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = new Booking();
        BusDTO busDTO=bookingDTO.getBus();
        
        Optional<Bus>optionalBus =busRepository.findById(busDTO.getId());
        optionalBus.ifPresent(bus -> booking.setBus(bus));
       MyUserDTO myUserDTO=bookingDTO.getUser();
        
        Optional<MyUser>optionalMyUser =userRepository.findById(myUserDTO.getId());
        optionalMyUser.ifPresent(Myuser -> booking.setUser(Myuser));
       // booking.setDateOfBooking(bookingDTO.getDateOfBooking());
        
        Optional<TravelSchedule>optionalTravelSchedule =
        		travelScheduleRepository.findById(bookingDTO.getSchedule().getId());
        optionalTravelSchedule.ifPresent(travelSchedule -> booking.setSchedule(travelSchedule));
        booking.setDateOfBooking(bookingDTO.getDateOfBooking());
        
        //booking.setSchedule(bookingDTO.getSchedule());
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setFareAmount(bookingDTO.getFareAmount());
       // booking.setBus(bookingDTO.getBus());
        booking.setRouteInfo(bookingDTO.getRouteInfo());
        //booking.setUser(bookingDTO.getUser());
        booking.setTotalAmount(bookingDTO.getTotalAmount());
        booking.setBookingStatus(bookingDTO.getBookingStatus());
        Booking b1=bookingRepository.save(booking);
        
        return ResponseEntity.ok(new BookingDTO(b1));
    }
    
    
}