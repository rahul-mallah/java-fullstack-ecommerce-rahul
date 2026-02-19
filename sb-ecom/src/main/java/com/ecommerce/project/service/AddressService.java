package com.ecommerce.project.service;

import com.ecommerce.project.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    public AddressDTO createAddress(AddressDTO addressDTO);

    public List<AddressDTO> getAllAddresses();

    public AddressDTO getAddressById(Long addressId);

    public List<AddressDTO> getUserAddresses();

    public AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO);

    public String deleteAddressById(Long addressId);
}
