package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address","addressId", addressId));

        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddresses() {
        User user = authUtil.loggedInUser();
        List<Address> addresses = user.getAddresses();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO) {
        Address currentAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address","addressId", addressId));

        if (addressDTO.getStreet() != null){
            currentAddress.setStreet(addressDTO.getStreet());
        }
        if (addressDTO.getZipcode() != null){
            currentAddress.setZipcode(addressDTO.getZipcode());
        }
        if (addressDTO.getBuilding() != null){
            currentAddress.setBuilding(addressDTO.getBuilding());
        }
        if (addressDTO.getCity() != null){
            currentAddress.setCity(addressDTO.getCity());
        }
        if (addressDTO.getState() != null){
            currentAddress.setState(addressDTO.getState());
        }
        if (addressDTO.getCountry() != null){
            currentAddress.setCountry(addressDTO.getCountry());
        }
        Address updatedAddress = addressRepository.save(currentAddress);

        User user = currentAddress.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddressById(Long addressId) {
        Address addressFromDB = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address","addressId", addressId));

        User user =  addressFromDB.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(addressFromDB);

        return "Address deleted successfully with addressId: " + addressId;
    }
}
