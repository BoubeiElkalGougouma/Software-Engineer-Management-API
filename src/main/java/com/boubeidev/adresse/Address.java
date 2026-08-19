package com.boubeidev.adresse;

import com.boubeidev.softwareEngineer.SoftwareEngineer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Address {
  @Id
  @GeneratedValue
  private Integer id;
  @Column(name = "street_name")
  @JsonProperty("streetName")
  private String streetName;

  @Column(name = "house_number")
  @JsonProperty("houseNumber")
  private String houseNumber;

  @Column(name = "zip_code")
  @JsonProperty("zipCode")
  private String zipCode;

  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
  private List<SoftwareEngineer> softwareEngineer;

  public Address() {}


  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return Objects.equals(id, address.id) && Objects.equals(streetName, address.streetName) && Objects.equals(houseNumber, address.houseNumber) && Objects.equals(zipCode, address.zipCode) && Objects.equals(softwareEngineer, address.softwareEngineer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, streetName, houseNumber, zipCode, softwareEngineer);
  }
}
