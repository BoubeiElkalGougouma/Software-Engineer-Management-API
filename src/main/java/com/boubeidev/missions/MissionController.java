package com.boubeidev.missions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/missions")
public class MissionController {
  private final MissionService missionService;

  public MissionController(MissionService missionService) {
    this.missionService = missionService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Mission> getMissions(){
    return missionService.getAllMissions();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mission getMission(@PathVariable int id){
    return missionService.getMissionById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mission addMission (@RequestBody Mission mission){
    return missionService.addNewMission(mission);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mission updateM(@PathVariable Integer id, @RequestBody Mission mission){
    return missionService.updateMission(id, mission);
  }

  @PatchMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public  Mission assignMissionToDep(@RequestParam Integer missId, @RequestParam Integer depId){
    return missionService.assignMissionToDepartment(missId, depId);
  }

}
