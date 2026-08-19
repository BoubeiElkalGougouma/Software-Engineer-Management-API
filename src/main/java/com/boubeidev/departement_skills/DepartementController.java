package com.boubeidev.departement_skills;

import com.boubeidev.softwareEngineer.SoftwareEngineerService;
import com.boubeidev.softwareEngineer.dto.SoftwareEngineerDto;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/departements")
public class DepartementController {
  private final DepartementService departementService;
  private final SoftwareEngineerService softwareEngineerService;

  public DepartementController(DepartementService departementService, SoftwareEngineerService softwareEngineerService) {
    this.departementService = departementService;
    this.softwareEngineerService = softwareEngineerService;
  }

  @GetMapping
  public List<Departement> getDepartements(){
    return departementService.getAllDepartements();
  }

  @GetMapping("/{dep_id}")
  public Departement getDepartById(@PathVariable Integer dep_id){
    return departementService.getDepartementById(dep_id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addDepart(@RequestBody Departement dep){
    departementService.addDepartement(dep);
  }

  @GetMapping("/search-by-departements")
  public List<SoftwareEngineerDto> filterByDepartement(@RequestParam String departement){
    return softwareEngineerService.filterEngineerByDepartement(departement);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public void removeDepart(@PathVariable Integer id){
    departementService.removeDepartement(id);
  }

}
