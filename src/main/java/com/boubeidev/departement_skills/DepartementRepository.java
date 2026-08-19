package com.boubeidev.departement_skills;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {
  Departement findDepartementById(Integer id);
  @Override
  List<Departement> findAll();

  void removeDepartementById(Integer id);

  Departement findByName(String name);
}
