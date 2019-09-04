package net.guides.springboot2.springboot2jpacrudexample.repository;

import net.guides.springboot2.springboot2jpacrudexample.model.IncompatiblePromed;
import net.guides.springboot2.springboot2jpacrudexample.model.Promed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncompatiblePromedRepository extends CrudRepository<IncompatiblePromed, Integer> {
    Optional<List<IncompatiblePromed>> findAllByPromed(Promed promed);


}

