package com.boubeidev.EngineerMission;

import com.boubeidev.EngineerMission.dto.AssignmentRequest;
import com.boubeidev.EngineerMission.dto.AssignmentResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/assignments")
public class SoftwareEngineerMissionController {

  private final SoftwareEngineerMissionService assignmentService;

  public SoftwareEngineerMissionController(SoftwareEngineerMissionService assignmentService) {
    this.assignmentService = assignmentService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('ROLE_ADMIN')") // VERIFICATAION DE ROLE ET DE L'AUTORITÉ
  public SoftwareEngineerMission assign(
    @RequestBody AssignmentRequest request) {

    return assignmentService.assignEngineerToMission(request);
  }

  @GetMapping
  public List<AssignmentResponse> getAll() {
    return assignmentService.getAllAssignments();
  }

  @PutMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public SoftwareEngineerMission updateAssign(@RequestBody @NonNull AssignmentRequest request) {
    return assignmentService.updateEngineerToMission(request);
  }

  @DeleteMapping("/engineer/{engId}/mission/{missId}")
  public void unassign(@PathVariable Integer engId, @PathVariable Integer missId) {
    assignmentService.deleteAssignment(engId, missId);
  }

}
