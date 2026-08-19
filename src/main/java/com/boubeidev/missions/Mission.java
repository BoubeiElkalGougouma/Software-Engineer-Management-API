package com.boubeidev.missions;

import com.boubeidev.EngineerMission.SoftwareEngineerMission;
import com.boubeidev.departement_skills.Departement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"id", "name", "duration", "assignments"})
public class Mission {

  @Id
  @GeneratedValue
  private Integer id;

  private  String name;
  private int duration;

  @ManyToMany
  @JoinTable(
    name = "departement_mission",
    joinColumns =@JoinColumn (name = "mission_id"),
    inverseJoinColumns = @JoinColumn(name = "departement_id")
  )
  @JsonIgnore
  private List<Departement> departement;

  @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SoftwareEngineerMission> assignments;

}
