package com.jeroka.address.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jeroka.address.model.Address;
import com.jeroka.address.model.AddressType;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
    List<Address> findByUserIdAndAddressType(Long userId, AddressType addressType);
    Optional<Address> findByUserIdAndAddressTypeAndId(Long userId, AddressType addressType, Long id);
} 