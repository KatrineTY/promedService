package net.guides.springboot2.springboot2jpacrudexample.services;

import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.Promed;
import net.guides.springboot2.springboot2jpacrudexample.model.PromedDto;

import java.util.List;

public interface PromedService {
    public List<Promed> getAllPromeds();

    public PromedDto getPromedById(Integer promedId) throws ResourceNotFoundException;

    public Promed createPromed(Promed promed);

    public Promed updatePromed(Integer promedId, PromedDto promedDetails) throws ResourceNotFoundException;

    public void deletePromed(Integer promedId) throws ResourceNotFoundException;

}
