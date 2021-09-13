package com.stoom.lucas.qualification.entity.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="address")
public class Address {

    @Id
    private Long id;

    private String streetName;

    /**
     * Negative imply S/N
     */
    private Integer number;

    private String complement;

    private String city;

    private String state;

    private String country;

    private String zipcode;

    @Column(precision = 8, scale = 6)
    private BigDecimal latitude;
    
    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;
}
