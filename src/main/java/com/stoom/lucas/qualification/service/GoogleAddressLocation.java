package com.stoom.lucas.qualification.service;

import java.io.IOException;
import java.util.Optional;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.stoom.lucas.qualification.controller.dto.AddressDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleAddressLocation implements AddressLocation {

    @Value("${stoom.google.api:AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw}")
    private String apiKey;

    @Override
    public AddressDTO getLocation(AddressDTO address) throws Exception {
        try {
            if(address.getLatitude().isEmpty() || address.getLongitude().isEmpty()){
                GeoApiContext context = new GeoApiContext.Builder().apiKey(this.apiKey).build();
                GeocodingResult[] results = GeocodingApi.geocode(context, this.getFormattedAddress(address)).await();

                if(results.length < 1){
                    throw new Exception(AddressLocation.ADDRESS_NOT_FOUND);
                }

                address.setLatitude(Optional.of(results[0].geometry.location.lat));
                address.setLongitude(Optional.of(results[0].geometry.location.lng));
            }
        } catch (ApiException e) {
            throw new Exception();
        } catch (InterruptedException | IOException e) {
            throw new Exception();
        }
        return address;
    }

    private String getFormattedAddress(AddressDTO address) {
        return String.format("%s %d %s, %s - %s, %s, $s", address.getStreetName(), address.getNumber(),
                address.getComplement(), address.getCity(), address.getState(), address.getZipcode(),
                address.getCountry());
    }
}
