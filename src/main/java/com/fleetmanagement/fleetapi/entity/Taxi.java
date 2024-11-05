package com.fleetmanagement.fleetapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "taxis")
public class Taxi {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "plate", length = 50)
    private String plate;

    public Taxi(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
