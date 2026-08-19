package com.boubeidev.departement_skills;

import com.boubeidev.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

import static java.util.Objects.isNull;


@Service
public class DepartementService {
  private final DepartementRepository departementRepository;


  public DepartementService(DepartementRepository departementRepository) {
    this.departementRepository = departementRepository;
  }

  public List<Departement> getAllDepartements(){
    return departementRepository.findAll();
  }

  public Departement getDepartementById(Integer id) {
    return departementRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Département non trouvé avec l'ID : " + id));
  }

  public void addDepartement(Departement dep){
    if(isNull(dep)) throw new RuntimeException("Information non fournie !");
    departementRepository.save(dep);
  }

  public void removeDepartement(Integer dep_id){
    departementRepository.removeDepartementById(dep_id);
  }


}
