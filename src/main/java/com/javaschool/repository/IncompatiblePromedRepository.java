package com.javaschool.repository;

import com.javaschool.model.IncompatiblePromed;
import com.javaschool.model.Promed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncompatiblePromedRepository extends CrudRepository<IncompatiblePromed, Integer> {
    Optional<List<IncompatiblePromed>> findAllByPromedOrIncompatiblePromed(Promed promed1, Promed promed2);


}

