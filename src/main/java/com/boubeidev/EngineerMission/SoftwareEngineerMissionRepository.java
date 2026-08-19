package com.boubeidev.EngineerMission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SoftwareEngineerMissionRepository extends JpaRepository<SoftwareEngineerMission, SoftwareEngineerMissionId> {

}
