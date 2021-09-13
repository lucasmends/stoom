package com.stoom.lucas.qualification.service;

import com.stoom.lucas.qualification.controller.dto.AddressDTO;

public interface AddressLocation {
  public static final String ADDRESS_NOT_FOUND = "Address not found";
  
  public AddressDTO getLocation(AddressDTO address) throws Exception;
}
