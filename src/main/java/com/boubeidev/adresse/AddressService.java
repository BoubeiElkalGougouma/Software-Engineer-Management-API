package com.boubeidev.adresse;

import org.springframework.stereotype.Service;

@Service
public class AddressService {

  private final AddressRespository addressRespository;

  public AddressService(AddressRespository addressRespository) {
    this.addressRespository = addressRespository;
  }

  public Address insertAddresses(Address address) {
    addressRespository.save(address);
    return address;
  }

  public Address updateAddress(Integer id, Address address){
    return addressRespository.findById(id)
      .map(existAddress -> {
        existAddress.setStreetName(address.getStreetName());
        existAddress.setHouseNumber(address.getHouseNumber());
        existAddress.setZipCode(address.getZipCode());
        existAddress.setSoftwareEngineer(address.getSoftwareEngineer());
        return addressRespository.save(existAddress);
      })
      .orElseThrow(() -> new RuntimeException("L'adresse avec l'id : " + id + " non trouvé"));
  }



}
