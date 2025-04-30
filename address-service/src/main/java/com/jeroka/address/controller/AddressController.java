package com.jeroka.address.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jeroka.address.dto.AddressDTO;
import com.jeroka.address.model.AddressType;
import com.jeroka.address.services.AddressService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/users/{userId}/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(
            @PathVariable Long userId,
            @RequestBody AddressDTO addressDTO) {
        addressDTO.setUserId(userId);
        AddressDTO createdAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.ok(createdAddress);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestParam AddressType type) {
        AddressDTO address = addressService.getAddressById(userId, type, id);
        return ResponseEntity.ok(address);
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddresses(
            @PathVariable Long userId,
            @RequestParam(required = false) AddressType type) {
        List<AddressDTO> addresses = type != null ?
            addressService.getAddressesByUserAndType(userId, type) :
            addressService.getAddressesByUser(userId);
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody AddressDTO addressDTO) {
        addressDTO.setUserId(userId);
        AddressDTO updatedAddress = addressService.updateAddress(userId, id, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long id) {
        addressService.deleteAddress(userId, id);
        return ResponseEntity.noContent().build();
    }
} 