package com.boubeidev.EngineerMission.dto;

import java.time.LocalDate;

public record AssignmentRequest(
  Integer engId,
  Integer missId,
  String role,
  LocalDate startDate,
  LocalDate endDate
) {

}
