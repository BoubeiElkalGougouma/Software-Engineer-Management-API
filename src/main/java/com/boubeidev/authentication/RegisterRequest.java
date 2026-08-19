package com.boubeidev.authentication;

import com.boubeidev.adresse.Address;
import com.boubeidev.departement_skills.Departement;
import com.boubeidev.softwareEngineer.Role;
import com.boubeidev.validators.ValidYear;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank(message = "L'email ne peut pas être vide")
  @Email(message = "Format d'email invalide")
  private String email;

  @NotBlank(message = "Le mot de passe est obligatoire !")
  @Size(min = 6, max = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
  private String password;

  String fullname;
  String techStack;
  Role role;
  Address address;
  Departement departement;

  @ValidYear(message = "Année invalide !")
  Integer startYear;

}
