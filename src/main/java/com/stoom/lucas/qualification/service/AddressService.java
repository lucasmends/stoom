package com.stoom.lucas.qualification.service;

import java.util.List;

import com.stoom.lucas.qualification.controller.dto.AddressDTO;
import com.stoom.lucas.qualification.entity.model.Address;
import com.stoom.lucas.qualification.entity.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AddressService {
    @Autowired
    private AddressLocation locateAddress;

    @Autowired
    private AddressRepository addressRepository;

    public Address addAdress(AddressDTO address) {
        if (this.addressRepository.existsById(address.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address already exists");
        }
        address = this.completeAddress(address);

        Address newAddress = AddressDTO.mapDTOToEntity(address);

        return this.addressRepository.save(newAddress);

    }

    public Address saveAdress(AddressDTO address) {
        this.findAddress(address.getId());
        
        address = this.completeAddress(address);

        Address newAddress = AddressDTO.mapDTOToEntity(address);

        return this.addressRepository.save(newAddress);
    }

    public Address findAddressById(Long id){
        return this.findAddress(Long.valueOf(id));
    }

    public List<Address> findAll(){
        return (List<Address>) this.addressRepository.findAll();
    }

    public void deleteAddress(Long id){
        Address address = this.findAddress(Long.valueOf(id));

        this.addressRepository.delete(address);
    }

    private Address findAddress(long id) {
        return this.addressRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private AddressDTO completeAddress(AddressDTO address){
        try {
            address = this.locateAddress.getLocation(address);
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage().equals(AddressLocation.ADDRESS_NOT_FOUND)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AddressLocation.ADDRESS_NOT_FOUND);
            }
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }

        return address;
    }
}
