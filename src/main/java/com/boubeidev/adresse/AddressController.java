package com.boubeidev.adresse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/addresses")
public class AddressController {

  private  final AddressService addressSevice;

  public AddressController(AddressService addressSevice) {
    this.addressSevice = addressSevice;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Address insertAddress(@RequestBody Address adress){
    return addressSevice.insertAddresses(adress);
  }

  @PutMapping("/{id}")
  public Address update_Ad (@PathVariable Integer id, @RequestBody Address address){
    return addressSevice.updateAddress(id, address);
  }

}
