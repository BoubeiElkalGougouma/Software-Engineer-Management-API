package com.boubeidev.EngineerMission;

import com.boubeidev.EngineerMission.dto.AssignmentRequest;
import com.boubeidev.EngineerMission.dto.AssignmentResponse;
import com.boubeidev.missions.Mission;
import com.boubeidev.missions.MissionRepository;
import com.boubeidev.softwareEngineer.SoftwareEngineer;
import com.boubeidev.softwareEngineer.SoftwareEngineerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoftwareEngineerMissionService {

  private final SoftwareEngineerMissionRepository softwareEngineerMissionRepository;
  private final SoftwareEngineerRepository engineerRepository;
  private final MissionRepository missionRepository;

  public SoftwareEngineerMissionService(SoftwareEngineerMissionRepository softwareEngineerMissionRepository, SoftwareEngineerRepository engineerRepository, MissionRepository missionRepository) {
    this.softwareEngineerMissionRepository = softwareEngineerMissionRepository;
    this.engineerRepository = engineerRepository;
    this.missionRepository = missionRepository;
  }

  @Transactional
  public SoftwareEngineerMission assignEngineerToMission(AssignmentRequest dto) {
    SoftwareEngineer engineer = fetchEngineer(dto.engId());
    Mission mission = fetchMission(dto.missId());

    validateSameDepartement(engineer, mission);

    SoftwareEngineerMission assignment = new SoftwareEngineerMission(engineer, mission);
    return applyDetailsAndSave(assignment, dto.role(), dto.startDate(), dto.endDate());
  }

  public List<AssignmentResponse> getAllAssignments() {
    return softwareEngineerMissionRepository.findAll().stream()
      .map(assignment -> new AssignmentResponse(
        assignment.getSoftwareEngineer().getId(),
        assignment.getSoftwareEngineer().getUsername(),
        assignment.getMission().getName(),
        assignment.getRoleInMission(),
        assignment.getStartDate(),
        assignment.getEndDate()
      ))
      .collect(Collectors.toList());
  }

  @Transactional
  public SoftwareEngineerMission updateEngineerToMission(AssignmentRequest dto) {
    SoftwareEngineerMission assignment = fetchAssignment(dto.engId(), dto.missId());
    return applyDetailsAndSave(assignment, dto.role(), dto.startDate(), dto.endDate());
  }

  @Transactional
  public void deleteAssignment(Integer engId, Integer missId) {
    SoftwareEngineerMissionId id = new SoftwareEngineerMissionId(engId, missId);

    if (!softwareEngineerMissionRepository.existsById(id)) {
      throw new RuntimeException("Cette affectation n'existe pas.");
    }
    softwareEngineerMissionRepository.deleteById(id);
  }


  private SoftwareEngineer fetchEngineer(Integer engineerId) {
    return engineerRepository.findById(engineerId)
      .orElseThrow(() -> new EntityNotFoundException("SoftwareEngineer introuvable : " + engineerId));
  }

  private Mission fetchMission(Integer missionId) {
    return missionRepository.findById(missionId)
      .orElseThrow(() -> new EntityNotFoundException("Mission introuvable : " + missionId));
  }

  private SoftwareEngineerMission fetchAssignment(Integer engId, Integer missId) {
    SoftwareEngineerMissionId id = new SoftwareEngineerMissionId(engId, missId);
    return softwareEngineerMissionRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Affectation introuvable"));
  }

  private SoftwareEngineerMission applyDetailsAndSave(SoftwareEngineerMission assignment,
                                                      String role,
                                                      LocalDate startDate,
                                                      LocalDate endDate) {
    assignment.setRoleInMission(role);
    assignment.setStartDate(startDate);
    assignment.setEndDate(endDate);
    return softwareEngineerMissionRepository.save(assignment);
  }

  private void validateSameDepartement(SoftwareEngineer engineer, Mission mission) {
    if (engineer.getDepartement() == null || mission.getDepartement() == null) {
      throw new IllegalStateException("L'ingénieur et la mission doivent avoir un département.");
    }

    boolean belongsToSameDept = mission.getDepartement().contains(engineer.getDepartement());

    if (!belongsToSameDept) {
      throw new IllegalArgumentException("L'ingénieur n'appartient pas au département de la mission.");
    }

  }



}
