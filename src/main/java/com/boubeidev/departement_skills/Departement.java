package com.boubeidev.departement_skills;

import com.boubeidev.EngineerMission.SoftwareEngineerMission;
import com.boubeidev.missions.Mission;
import com.boubeidev.softwareEngineer.SoftwareEngineer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Departement {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;

  @OneToMany(mappedBy = "departement")
  @JsonIgnore
  private List<SoftwareEngineer> softwareEngineers;

  @ManyToMany(mappedBy = "departement")
  @JsonIgnore
  private List<Mission> engineerMissions;

}
