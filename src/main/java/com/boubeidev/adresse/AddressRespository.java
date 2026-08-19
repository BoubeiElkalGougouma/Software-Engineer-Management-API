package com.boubeidev.adresse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRespository extends JpaRepository<Address, Integer> {
  
  Optional<Address> findById(Integer integer);
  
}
