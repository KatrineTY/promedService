package com.javaschool.repository;

import com.javaschool.model.Promed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromedRepository extends JpaRepository<Promed, Integer> {
    Optional<Promed> findPromedByName(String name);
}
