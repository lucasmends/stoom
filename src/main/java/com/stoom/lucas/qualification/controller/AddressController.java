package com.stoom.lucas.qualification.controller;

import com.stoom.lucas.qualification.controller.dto.AddressDTO;
import com.stoom.lucas.qualification.service.AddressService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public CollectionModel<AddressDTO> getMethodName() {
        List<AddressDTO> addresses = this.addressService.findAll().stream().map(AddressDTO::mapEntityToDTO)
                .collect(Collectors.toList());

        for (AddressDTO add : addresses) {
            Link selfLink = linkTo(methodOn(AddressController.class).getAddress(add.getId())).withSelfRel();
            add.add(selfLink);
        };

        return CollectionModel.of(addresses);
    }

    @PostMapping
    public EntityModel<AddressDTO> createAddress(@RequestBody AddressDTO address) {
        address = AddressDTO.mapEntityToDTO(this.addressService.addAdress(address));
        return EntityModel.of(address,
                linkTo(methodOn(AddressController.class).getAddress(address.getId())).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public EntityModel<AddressDTO> getAddress(@PathVariable long id) {
        AddressDTO address = AddressDTO.mapEntityToDTO(this.addressService.findAddressById(id));
        return EntityModel.of(address,
                linkTo(methodOn(AddressController.class).getAddress(address.getId())).withSelfRel());
    }

    @PutMapping
    public EntityModel<AddressDTO> updateAddress(@RequestBody AddressDTO address) {
        address = AddressDTO.mapEntityToDTO(this.addressService.saveAdress(address));
        return EntityModel.of(address,
                linkTo(methodOn(AddressController.class).getAddress(address.getId())).withSelfRel());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteAddress(@PathVariable long id) {

        this.addressService.deleteAddress(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
