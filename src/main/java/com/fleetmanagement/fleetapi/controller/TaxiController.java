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

import java.util.Collections;
import java.util.List;

@RestController
public class TaxiController {

    @Autowired
    private TaxiService taxiService;

    @GetMapping("/taxis")
    public ResponseEntity<?> findByPlateContainingIgnoreCase(@RequestParam(required = false) String plate,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int limit) {

        // Validación de parámetros
        if (page < 0 || limit <= 0) {
            ErrorResponse errorResponse = new ErrorResponse("page or limit is not valid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Obtener la lista de taxis
        List<Taxi> taxis = taxiService.findByPlateContainingIgnoreCase(plate);

        // Comprobar si hay taxis en la lista
        if (taxis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No taxis found for the given plate."));
        }

        // Calcular el inicio y el fin para la paginación
        int start = page * limit;
        int end = Math.min((start + limit), taxis.size());

        // Comprobar si el rango es válido
        if (start >= taxis.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No taxis found in the specified page."));
        }

        // Obtener la sublista de taxis para la página solicitada
        List<Taxi> paginatedTaxis = taxis.subList(start, end);

        return ResponseEntity.ok(paginatedTaxis);
    }
}
