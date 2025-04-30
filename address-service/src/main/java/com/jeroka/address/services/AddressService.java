package com.jeroka.address.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jeroka.address.dto.AddressDTO;
import com.jeroka.address.model.Address;
import com.jeroka.address.model.AddressType;
import com.jeroka.address.repository.AddressRepository;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = convertToEntity(addressDTO);
        Address savedAddress = addressRepository.save(address);
        return convertToDTO(savedAddress);
    }

    public AddressDTO getAddressById(Long userId, AddressType addressType, Long id) {
        Address address = addressRepository.findByUserIdAndAddressTypeAndId(userId, addressType, id)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + id + " for user: " + userId));
        return convertToDTO(address);
    }

    public List<AddressDTO> getAddressesByUser(Long userId) {
        return addressRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AddressDTO> getAddressesByUserAndType(Long userId, AddressType addressType) {
        return addressRepository.findByUserIdAndAddressType(userId, addressType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AddressDTO updateAddress(Long userId, Long id, AddressDTO addressDTO) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + id));
        
        if (!existingAddress.getUserId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user: " + userId);
        }

        updateEntityFromDTO(existingAddress, addressDTO);
        Address updatedAddress = addressRepository.save(existingAddress);
        return convertToDTO(updatedAddress);
    }

    public void deleteAddress(Long userId, Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + id));
        
        if (!address.getUserId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user: " + userId);
        }
        
        addressRepository.deleteById(id);
    }

    private Address convertToEntity(AddressDTO dto) {
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setZipCode(dto.getZipCode());
        address.setUserId(dto.getUserId());
        address.setAddressType(dto.getAddressType());
        return address;
    }

    private AddressDTO convertToDTO(Address entity) {
        return new AddressDTO(
            entity.getId(),
            entity.getStreet(),
            entity.getCity(),
            entity.getState(),
            entity.getCountry(),
            entity.getZipCode(),
            entity.getUserId(),
            entity.getAddressType()
        );
    }

    private void updateEntityFromDTO(Address entity, AddressDTO dto) {
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setZipCode(dto.getZipCode());
        entity.setAddressType(dto.getAddressType());
        // Ne pas mettre à jour l'userId pour éviter le changement de propriétaire
    }
} 