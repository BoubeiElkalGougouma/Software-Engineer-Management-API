package com.boubeidev.authentication;

import com.boubeidev.softwareEngineer.SoftwareEngineer;
import com.boubeidev.softwareEngineer.SoftwareEngineerRepository;
import com.boubeidev.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.UnknownNullability;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final SoftwareEngineerRepository engineerRepo;
  private final JwtUtil jwtUtils;

  private final AuthenticationManager authenticationManager;

  private final PasswordEncoder passwordEncoder;

  public AuthResponse register(@Valid @UnknownNullability RegisterRequest request) {

    if (engineerRepo.findByEmail(request.getEmail()).isPresent()) {
      throw new IllegalArgumentException("Cet email est déjà associé à un compte.");
    }

    SoftwareEngineer engineer = new SoftwareEngineer();

    engineer.setEmail(request.getEmail());
    engineer.setPassword(passwordEncoder.encode(request.getPassword()));
    engineer.setFullname(request.getFullname());
    engineer.setRole(request.getRole());
    engineer.setUsername(request.getEmail());
    engineer.setTechStack(request.getTechStack());
    engineer.setDepartement(request.getDepartement());
    engineer.setAddress(request.getAddress());
    engineer.setYear(request.getStartYear());

    engineerRepo.save(engineer);

    String jwtToken = jwtUtils.generateToken(engineer.getEmail());

    return new AuthResponse(jwtToken);
  }


  public AuthResponse login(@Valid @UnknownNullability LoginRequest request) {
    // 1. Authentifier l'utilisateur via Spring Security
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.email(),
        request.password()
      )
    );
    String token = jwtUtils.generateToken(request.email());

    return new AuthResponse(token);
  }


}
