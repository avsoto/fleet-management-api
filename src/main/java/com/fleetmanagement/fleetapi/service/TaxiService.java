package com.fleetmanagement.fleetapi.service;

import com.fleetmanagement.fleetapi.entity.Taxi;
import com.fleetmanagement.fleetapi.repository.TaxiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxiService{

    @Autowired
    private TaxiRepository taxiRepository;

    public List<Taxi> findByPlateContainingIgnoreCase(String plate) {
        if (plate == null || plate.isEmpty()) {
            // If a license plate is not provided, return all taxis
            return taxiRepository.findAll();
        }
        return taxiRepository.findByPlateContainingIgnoreCase(plate);
    }

}
