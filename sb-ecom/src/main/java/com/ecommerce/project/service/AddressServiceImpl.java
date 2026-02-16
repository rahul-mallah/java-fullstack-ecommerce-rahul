package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = modelMapper.map(addressDTO, Address.class);

        boolean isAddressPresent = false;
        User user = authUtil.loggedInUser();
        List <Address> addresses = user.getAddresses();
        for (Address addr : addresses) {
            if (addr.getStreet().equals(address.getStreet())
            && addr.getBuilding().equals(address.getBuilding())
            && addr.getCity().equals(address.getCity())
            && addr.getState().equals(address.getState())
            && addr.getCountry().equals(address.getCountry())
            && addr.getZipcode().equals(address.getZipcode())) {
                isAddressPresent = true;
                break;
            }
        }
        if (!isAddressPresent) {
            addresses.add(address);
            user.setAddresses(addresses);

            address.setUser(user);

            Address savedAddress = addressRepository.save(address);

            return modelMapper.map(savedAddress, AddressDTO.class);
        } else {
            throw new APIException("Same address already exists for this user!!!");
        }
    }
}
