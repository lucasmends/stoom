package com.stoom.lucas.qualification.controller.dto;

import java.math.BigDecimal;
import java.util.Optional;

import com.stoom.lucas.qualification.entity.model.Address;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@Data
public class AddressDTO extends RepresentationModel<AddressDTO>{

    private Long id;

    private String streetName;

    private Integer number;

    private String complement;

    private String city;

    private String state;

    private String country;

    private String zipcode;

    private Optional<Double> latitude;

    private Optional<Double> longitude;

    public static AddressDTO mapEntityToDTO(Address address) {
        AddressDTO dto = new AddressDTO();

        dto.setId(address.getId());
        dto.setStreetName(address.getStreetName());
        dto.setNumber(address.getNumber());
        dto.setComplement(address.getComplement());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        dto.setZipcode(address.getZipcode());
        dto.setLatitude(Optional.of(address.getLatitude().doubleValue()));
        dto.setLongitude(Optional.of(address.getLongitude().doubleValue()));

        return dto;
    }

    public static Address mapDTOToEntity(AddressDTO dto) {
        Address address = new Address();

        address.setId(dto.getId());
        address.setStreetName(dto.getStreetName());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setZipcode(dto.getZipcode());

        BigDecimal latitude = BigDecimal.valueOf(dto.getLatitude().orElseThrow());
        BigDecimal longitude = BigDecimal.valueOf(dto.getLongitude().orElseThrow());

        address.setLatitude(latitude);
        address.setLongitude(longitude);

        return address;
    }
}
