package com.boubeidev.missions;

import com.boubeidev.departement_skills.Departement;
import com.boubeidev.departement_skills.DepartementRepository;
import com.boubeidev.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static jdk.dynalink.linker.support.Guards.isNotNull;

@Service
public class MissionService {
  private final MissionRepository missionRepository;
  private final DepartementRepository departementRepository;

  public MissionService(MissionRepository missionRepository, DepartementRepository departementRepository) {
    this.missionRepository = missionRepository;
    this.departementRepository = departementRepository;
  }

  public List<Mission> getAllMissions(){
    List<Mission> missions = missionRepository.findAll();
    if (missions.isEmpty()) {
      throw new EntityNotFoundException("Aucune mission trouvée !");
    }
    return missions;
  }

  public Mission getMissionById(int id){
    return missionRepository.findById(id).orElseThrow(() -> new IllegalStateException("La mission " + id + "non trouvée"));
  }

  public Mission addNewMission(Mission mission){
    return missionRepository.save(mission);
  }

  public Mission updateMission(Integer id, Mission mission){
    return missionRepository.findById(id)
      .map(miss -> {
        miss.setName(mission.getName());
        miss.setDuration(mission.getDuration());
        return missionRepository.save(miss);
      })
      .orElseThrow(() -> new RuntimeException("La mission " + id + " non trouvé"));
  }

  @Transactional
  public Mission assignMissionToDepartment(Integer missionId, Integer departmentId) {
    Mission mission = fetchMission(missionId);
    Departement departement = fetchDepartment(departmentId);
    departement.getEngineerMissions().add(mission);
    mission.getDepartement().add(departement);
    missionRepository.save(mission);

    return mission;
  }

  private Mission fetchMission(Integer missionId) {
    return missionRepository.findById(missionId)
      .orElseThrow(() -> new EntityNotFoundException("Mission introuvable avec l'ID : " + missionId));
  }

  private Departement fetchDepartment(Integer departmentId) {
    return departementRepository.findById(departmentId)
      .orElseThrow(() -> new EntityNotFoundException("Département introuvable avec l'ID : " + departmentId));
  }



}
