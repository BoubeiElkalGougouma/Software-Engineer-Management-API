package com.boubeidev.softwareEngineer;

import com.boubeidev.adresse.Address;
import com.boubeidev.adresse.AddressRespository;
import com.boubeidev.departement_skills.Departement;
import com.boubeidev.departement_skills.DepartementRepository;
import com.boubeidev.exceptions.EntityNotFoundException;
import com.boubeidev.softwareEngineer.dto.SoftwareEngineerDto;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService implements UserDetailsService {

  private final SoftwareEngineerRepository softwareEngineerRepository;
  private final DepartementRepository departementRepository;
  private final AddressRespository addressRespository;

  public SoftwareEngineerService(
    SoftwareEngineerRepository softwareEngineerRepository,
    DepartementRepository departementRepository,
    AddressRespository addressRespository) {
    this.softwareEngineerRepository = softwareEngineerRepository;
    this.departementRepository = departementRepository;
    this.addressRespository = addressRespository;
  }

  public List<SoftwareEngineerDto> getAllSoftwareEngineers() {
    List<SoftwareEngineer> engineers = softwareEngineerRepository.findAll();

    if (engineers.isEmpty()) {
      throw new EntityNotFoundException("Aucun ingénieur trouvé !");
    }

    return engineers.stream()
      .map(this::convertToDto)
      .toList();
  }

  public SoftwareEngineerDto getSoftwareEngineerById(Integer id) {
    SoftwareEngineer engineer = findEngineerEntityById(id);
    return convertToDto(engineer);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return softwareEngineerRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
  }

  @Transactional
  public SoftwareEngineerDto insertSoftwareEngineer(SoftwareEngineerDto dto) {
    SoftwareEngineer engineer = new SoftwareEngineer();
    mapDtoToEntity(dto, engineer);
    SoftwareEngineer savedEngineer = softwareEngineerRepository.save(engineer);
    return convertToDto(savedEngineer);
  }

  @Transactional
  public SoftwareEngineerDto updateInfoSoftwareEngineers(Integer id, @NonNull SoftwareEngineerDto dto) {
    SoftwareEngineer existingEngineer = findEngineerEntityById(id);
    mapDtoToEntity(dto, existingEngineer);
    SoftwareEngineer updatedEngineer = softwareEngineerRepository.save(existingEngineer);
    return convertToDto(updatedEngineer);
  }

  public List<SoftwareEngineerDto> filterEngineerByTechStack(String techStack) {
    return softwareEngineerRepository.filterSoftwareEngineerByTechStack(techStack).stream()
      .map(this::convertToDto)
      .toList();
  }

  public List<SoftwareEngineerDto> filterEngineerByDepartement(String departement) {
    return softwareEngineerRepository.filterSoftwareEngineerByDepartement(departement).stream()
      .map(this::convertToDto)
      .toList();
  }

  public List<SoftwareEngineerDto> findEngineersWithoutMission() {
    return softwareEngineerRepository.findByMissionsIsEmpty().stream()
      .map(this::convertToDto)
      .toList();
  }

  @Transactional
  public SoftwareEngineer dropEngineer(Integer engineerId) {
    return softwareEngineerRepository.deleteSoftwareEngineerById(engineerId);
  }

  private void mapDtoToEntity(SoftwareEngineerDto dto, SoftwareEngineer engineer) {
    engineer.setFullname(dto.fullname());
    engineer.setUsername(dto.email());
    engineer.setEmail(dto.email());
    engineer.setTechStack(dto.techStack());
    engineer.setRole(dto.role());
    engineer.setYear(dto.startYear());

    if (dto.addressId() != null) {
      Address address = addressRespository.findById(dto.addressId())
        .orElseThrow(() -> new EntityNotFoundException("Adresse introuvable pour l'ID : " + dto.addressId()));
      engineer.setAddress(address);
    }

    if (dto.departement() != null) {
      Departement departement = departementRepository.findByName(dto.departement());
      if (departement == null) {
        throw new EntityNotFoundException("Département introuvable : " + dto.departement());
      }
      engineer.setDepartement(departement);
    }
  }

  private SoftwareEngineer findEngineerEntityById(Integer id) {
    return softwareEngineerRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Ingénieur non trouvé avec l'ID : " + id));
  }

  private SoftwareEngineerDto convertToDto(SoftwareEngineer engineer) {

    return new SoftwareEngineerDto(
      engineer.getFullname(),
      engineer.getEmail(),
      engineer.getUsername(),
      engineer.getTechStack(),
      engineer.getRole(),
      engineer.getAddress().getId(),
      engineer.getYear(),
      engineer.getDepartement().getName()
    );
  }
}