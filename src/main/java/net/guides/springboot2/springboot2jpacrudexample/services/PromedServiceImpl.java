package net.guides.springboot2.springboot2jpacrudexample.services;

import lombok.NoArgsConstructor;
import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.IncompatiblePromed;
import net.guides.springboot2.springboot2jpacrudexample.model.Promed;
import net.guides.springboot2.springboot2jpacrudexample.model.PromedDto;
import net.guides.springboot2.springboot2jpacrudexample.repository.IncompatiblePromedRepository;
import net.guides.springboot2.springboot2jpacrudexample.repository.PromedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class PromedServiceImpl implements PromedService {
    @Autowired
    private PromedRepository promedRepository;
    @Autowired
    private IncompatiblePromedRepository incompatiblePromedRepository;


    @Override
    public List<Promed> getAllPromeds() {
        return promedRepository.findAll();
    }

    @Override
    public PromedDto getPromedById(Integer promedId) throws ResourceNotFoundException {
        Promed promed = promedRepository.findById(promedId)
                .orElseThrow(() -> new ResourceNotFoundException("Promed not found for this id :: " + promedId));
        List<IncompatiblePromed> incompatiblePromeds = incompatiblePromedRepository.findAllByPromed(promed).orElse(new ArrayList<>());
        PromedDto promedDto = new PromedDto(promed, new ArrayList<>());
        if (!incompatiblePromeds.isEmpty()) {
            promedDto.setIncompatiblePromeds(incompatiblePromeds.stream()
                    .map(IncompatiblePromed::getIncompatiblePromed)
                    .collect(Collectors.toList()));
        }
        return promedDto;
    }

    @Override
    public Promed createPromed(Promed promed) {
        return promedRepository.save(promed);
    }

    @Override
    public Promed updatePromed(Integer promedId, PromedDto promedDetails) throws ResourceNotFoundException {
        promedDetails.setIncompatiblePromeds(promedDetails.getIncompatiblePromeds().stream()
                .filter(promed -> promed.getName() != null).collect(Collectors.toList()));
        Promed promed = promedRepository.findById(promedId)
                .orElseThrow(() -> new ResourceNotFoundException("Promed not found for this id :: " + promedId));
        List<IncompatiblePromed> oldIncompatiblePromeds = incompatiblePromedRepository.findAllByPromed(promed).orElse(new ArrayList<>());

        promed.setCount(promedDetails.getPromed().getCount());
        promed.setKind(promedDetails.getPromed().getKind());
        promed.setName(promedDetails.getPromed().getName());

        final Promed updatedPromed = promedRepository.save(promed);
        if (!oldIncompatiblePromeds.isEmpty()) {
            incompatiblePromedRepository.deleteAll(oldIncompatiblePromeds);
        }

        List<IncompatiblePromed> newIncompatiblePromeds = new ArrayList<>();
        for (Promed incompatiblePromed : promedDetails.getIncompatiblePromeds()) {
            Promed incPromed = promedRepository.findPromedByName(incompatiblePromed.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Promed not found for this id :: " + incompatiblePromed.getId()));
            newIncompatiblePromeds.add(new IncompatiblePromed(updatedPromed, incPromed));
        }

        incompatiblePromedRepository.saveAll(newIncompatiblePromeds);
        return updatedPromed;
    }

    @Override
    public void deletePromed(Integer promedId) throws ResourceNotFoundException {
        Promed promed = promedRepository.findById(promedId)
                .orElseThrow(() -> new ResourceNotFoundException("Promed not found for this id :: " + promedId));

        List<IncompatiblePromed> incompatiblePromeds = incompatiblePromedRepository.findAllByPromed(promed).orElse(new ArrayList<>());
        if (!incompatiblePromeds.isEmpty()) {
            incompatiblePromedRepository.deleteAll(incompatiblePromeds);
        }
        promedRepository.delete(promed);
    }
}
