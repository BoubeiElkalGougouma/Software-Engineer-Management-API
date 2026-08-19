package com.boubeidev.softwareEngineer;

import com.boubeidev.security.JwtUtil;
import com.boubeidev.softwareEngineer.dto.SoftwareEngineerDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/software_engineers")
public class SoftwareEngineerController {

  private final JwtUtil jwtUtil;
  private final SoftwareEngineerService softwareEngineerService;

  public SoftwareEngineerController(JwtUtil jwtUtil, SoftwareEngineerService softwareEngineerService) {
    this.jwtUtil = jwtUtil;
    this.softwareEngineerService = softwareEngineerService;
  }

  @GetMapping
  public ResponseEntity<List<SoftwareEngineerDto>> getEngineers(){
    List<SoftwareEngineerDto> engineers = softwareEngineerService.getAllSoftwareEngineers();
    return ResponseEntity.ok(engineers);
  }

  @GetMapping("{id}")
  @PreAuthorize("#id == authentication.principal.id")
  public SoftwareEngineerDto getEngineerById(@PathVariable Integer id){
    return softwareEngineerService.getSoftwareEngineerById(id);
  }

  @PostMapping
  public ResponseEntity<SoftwareEngineerDto> addNewSoftwareEngineer(@Valid @RequestBody SoftwareEngineerDto engineerDto){
    SoftwareEngineerDto savedDto = softwareEngineerService.insertSoftwareEngineer(engineerDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<SoftwareEngineerDto> updateEngineer(@PathVariable Integer id, @RequestBody SoftwareEngineerDto engineerDto){
    SoftwareEngineerDto updatedDto = softwareEngineerService.updateInfoSoftwareEngineers(id, engineerDto);
    return ResponseEntity.ok(updatedDto);
  }

  @GetMapping("/search-by-techStack")
  public List<SoftwareEngineerDto> filterByTech(@RequestParam String techStack) {
    return softwareEngineerService.filterEngineerByTechStack(techStack);
  }

  @GetMapping("/filter-by-missing-missions")
  public ResponseEntity<List<SoftwareEngineerDto>> findEngWithoutMission(){
    List<SoftwareEngineerDto> engineers = softwareEngineerService.findEngineersWithoutMission();
    return ResponseEntity.ok(engineers);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public SoftwareEngineer dropEng(@PathVariable Integer id){
    return softwareEngineerService.dropEngineer(id);
  }
}
