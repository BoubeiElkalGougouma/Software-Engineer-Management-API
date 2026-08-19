package com.boubeidev.softwareEngineer.dto;

import com.boubeidev.softwareEngineer.Role;

public record SoftwareEngineerDto(
   String fullname,
   String email,
   String username,
   String techStack,
   Role role,
   Integer addressId,
   int startYear,
   String departement
) {

}
