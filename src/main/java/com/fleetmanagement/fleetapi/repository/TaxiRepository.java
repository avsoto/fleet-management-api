package com.fleetmanagement.fleetapi.repository;

import com.fleetmanagement.fleetapi.entity.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Integer>{
    List<Taxi> findByPlateContainingIgnoreCase(String plate);
}
