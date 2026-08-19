package com.boubeidev.softwareEngineer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SoftwareEngineerRepository extends
                                    JpaRepository <SoftwareEngineer, Integer>{
  Optional<SoftwareEngineer> findByEmail(String email);

  @Query("SELECT u FROM SoftwareEngineer u WHERE LOWER(u.techStack) LIKE LOWER(CONCAT('%', :techStack, '%'))")
  List<SoftwareEngineer>  filterSoftwareEngineerByTechStack(@Param("techStack") String techStack);

  SoftwareEngineer deleteSoftwareEngineerById(Integer engineerId);

  @Query("SELECT u FROM SoftwareEngineer u WHERE LOWER(u.departement.name) LIKE LOWER(CONCAT('%',:departement, '%')) ")
  List<SoftwareEngineer> filterSoftwareEngineerByDepartement(@Param("departement") String departement);

  @Query("SELECT s FROM SoftwareEngineer s WHERE s.assignments IS EMPTY")
  List<SoftwareEngineer> findByMissionsIsEmpty();
}
