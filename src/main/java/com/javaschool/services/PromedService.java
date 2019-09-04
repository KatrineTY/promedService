package com.javaschool.services;

import com.javaschool.exception.ResourceNotFoundException;
import com.javaschool.model.Promed;
import com.javaschool.model.PromedDto;

import java.util.List;

public interface PromedService {
    public List<Promed> getAllPromeds();

    public PromedDto getPromedById(Integer promedId) throws ResourceNotFoundException;

    public Promed createPromed(Promed promed);

    public Promed updatePromed(Integer promedId, PromedDto promedDetails) throws ResourceNotFoundException;

    public void deletePromed(Integer promedId) throws ResourceNotFoundException;

    void subtractCount(String promedName, int count);
}
