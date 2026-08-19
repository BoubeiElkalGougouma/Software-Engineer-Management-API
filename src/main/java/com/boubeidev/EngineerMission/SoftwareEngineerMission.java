package com.boubeidev.EngineerMission;

import com.boubeidev.missions.Mission;
import com.boubeidev.softwareEngineer.SoftwareEngineer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.time.LocalDate;

@Entity
@Table(name = "software_engineer_mission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftwareEngineerMission {

  @EmbeddedId
  private SoftwareEngineerMissionId id = new SoftwareEngineerMissionId();

  @Column(name = "role_in_mission", nullable = false)
  private String roleInMission;

  private LocalDate startDate;

  private LocalDate endDate;


  @ManyToOne
  @MapsId("softwareId")
  @JoinColumn(name = "software_id")
  @JsonIgnoreProperties("assignments")
  private SoftwareEngineer softwareEngineer;

  @ManyToOne
  @MapsId("missionId")
  @JoinColumn(name = "mission_id")
  @JsonIgnoreProperties("assignments")
  private Mission mission;


  public SoftwareEngineerMission(@NonNull SoftwareEngineer softwareEngineer, @NonNull Mission mission) {
    this.softwareEngineer = softwareEngineer;
    this.mission = mission;
    this.id = new SoftwareEngineerMissionId();
  }

}
