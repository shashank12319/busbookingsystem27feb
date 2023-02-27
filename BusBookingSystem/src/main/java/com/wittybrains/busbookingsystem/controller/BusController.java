package com.wittybrains.busbookingsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wittybrains.busbookingsystem.dto.BusDTO;
import com.wittybrains.busbookingsystem.model.BookingStatus;
import com.wittybrains.busbookingsystem.model.Bus;
import com.wittybrains.busbookingsystem.model.Seat;
import com.wittybrains.busbookingsystem.repository.BusRepository;

@RestController
@RequestMapping("/bus")
public class BusController {
	
    private final BusRepository busRepository;
    
    public BusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @PostMapping("/createBuses")
    public ResponseEntity<List<BusDTO>> createBuses(@RequestBody List<BusDTO> busList) {
        try {
            List<BusDTO> createdBuses = new ArrayList<>();
            for (BusDTO busDTO : busList) {
                Bus bus = new Bus();
                bus.setDestination(busDTO.getDestination());
                bus.setNumber(busDTO.getNumber());
                bus.setSource(busDTO.getSource());
                bus.setType(busDTO.getType());
                bus.setTiming(busDTO.getTiming());
                bus.setNumberOfSeats(busDTO.getNumberOfSeats());
                // Create seats for the bus
                List<Seat> seats = new ArrayList<>();
                for (int i = 1; i <= busDTO.getNumberOfSeats(); i++) {
                    Seat seat = new Seat();
                    seat.setSeatNumber(String.valueOf(i));
                   // seat.setBookingStatus(BookingStatus.AVAILABLE);
                   // seat.setSeatType(busDTO.getSeatType());
                    seat.setPrice(busDTO.getPrice());
                    seat.setBus(bus);
                    seats.add(seat);
                }
                bus.setSeats(seats);

                busRepository.save(bus);
                createdBuses.add(new BusDTO(bus));
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBuses);
        } catch (Exception e) {
        	
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}