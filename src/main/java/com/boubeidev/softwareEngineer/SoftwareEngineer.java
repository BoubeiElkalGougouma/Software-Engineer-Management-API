package com.boubeidev.softwareEngineer;

import com.boubeidev.adresse.Address;
import com.boubeidev.departement_skills.Departement;
import com.boubeidev.EngineerMission.SoftwareEngineerMission;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareEngineer implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String password;

  private String fullname;
  private String username;
  private String email;
  private String techStack;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  private int year;

  @ManyToOne
  @JoinColumn(name = "address_id")
  @JsonIgnoreProperties("softwareEngineer")
  private Address address;

  @ManyToOne
  @JoinColumn(name="departement_id")
  private Departement departement;

  @OneToMany(mappedBy = "softwareEngineer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SoftwareEngineerMission> assignments;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() { return true; }

  @Override
  public boolean isAccountNonLocked() { return true; }

  @Override
  public boolean isCredentialsNonExpired() { return true; }

  @Override
  public boolean isEnabled() { return true; }

}
