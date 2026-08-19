package com.boubeidev.EngineerMission.dto;

import java.time.LocalDate;

public record AssignmentResponse (
   Integer engineerId,
   String engineerName,
   String missionName,
   String role,
   LocalDate startDate,
   LocalDate endDate) {
}
