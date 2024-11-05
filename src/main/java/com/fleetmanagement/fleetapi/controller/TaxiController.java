package com.fleetmanagement.fleetapi.controller;

import com.fleetmanagement.fleetapi.ErrorResponse;
import com.fleetmanagement.fleetapi.entity.Taxi;
import com.fleetmanagement.fleetapi.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TaxiController {

    @Autowired
    private TaxiService taxiService;

    @GetMapping("/taxis")
    public ResponseEntity<?> findByPlateContainingIgnoreCase(@RequestParam(required = false) String plate,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int limit) {

        // Validate parameters
        if (page < 0 || limit <= 0) {
            ErrorResponse errorResponse = new ErrorResponse("page or limit is not valid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Get the list of taxis
        List<Taxi> taxis = taxiService.findByPlateContainingIgnoreCase(plate);

        // Check if there are taxis listed
        if (taxis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No taxis found for the given plate."));
        }

        // Calculate start and end for pagination
        int start = page * limit;
        int end = Math.min((start + limit), taxis.size());

        // Check if the range is valid
        if (start >= taxis.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No taxis found in the specified page."));
        }

        // Get the taxi sublist for the requested page
        List<Taxi> paginatedTaxis = taxis.subList(start, end);

        return ResponseEntity.ok(paginatedTaxis);
    }
}
