package com.boubeidev.EngineerMission;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareEngineerMissionId implements Serializable {
  private Integer softwareId;
  private Integer missionId;

}
